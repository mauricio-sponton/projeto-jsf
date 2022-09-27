package br.com.mbs.projeto_jsf.controllers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import br.com.mbs.projeto_jsf.dao.DAOGenerico;
import br.com.mbs.projeto_jsf.model.Estado;
import br.com.mbs.projeto_jsf.model.Pessoa;
import br.com.mbs.projeto_jsf.repository.CidadeRepository;
import br.com.mbs.projeto_jsf.repository.CidadeRepositoryImpl;
import br.com.mbs.projeto_jsf.repository.EstadoRepository;
import br.com.mbs.projeto_jsf.repository.EstadoRepositoryImpl;
import br.com.mbs.projeto_jsf.repository.PessoaRepository;
import br.com.mbs.projeto_jsf.repository.PessoaRepositoryImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DAOGenerico<Pessoa> DAOGenerico = new DAOGenerico<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private PessoaRepository pessoaRepository = new PessoaRepositoryImpl();
	private EstadoRepository estadoRepository = new EstadoRepositoryImpl();
	private CidadeRepository cidadeRepository = new CidadeRepositoryImpl();
	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	private Part arquivo;

	public String salvar() throws IOException {
		if (pessoa.getId() != null) {
			mostrarMensagem("Edição efetuada com sucesso!");
		} else {
			mostrarMensagem("Usuário cadastrado com sucesso!");

		}
		
		processarImagem();
		
		pessoa = DAOGenerico.merge(pessoa);
		carregarPessoas();

		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}

	public void editar() {
		if (pessoa.getCidade() != null) {
			Estado estado = pessoa.getCidade().getEstado();

			if (estado != null) {
				pessoa.setEstado(estado);
				cidades = cidadeRepository.listarCidades(estado.getId());
			}
		}
	}

	public String remove() {
		DAOGenerico.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMensagem("Usuário excluído!");
		return "";
	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = DAOGenerico.getListEntity(Pessoa.class);
	}

	public String logar() {

		Pessoa pessoaEcontrada = pessoaRepository.findByLoginESenha(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaEcontrada != null) {
			ExternalContext externalContext = getContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaEcontrada);
			return "cadastro-pessoa.jsf";
		}

		return "index.jsf";
	}

	public String deslogar() {

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");

		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		request.getSession().invalidate();

		return "index.jsf";
	}

	public boolean permitirAcesso(String acesso) {
		ExternalContext externalContext = getContext();
		Pessoa pessoaLogada = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

		return pessoaLogada.getPerfil().equals(acesso);
	}

	public void pesquisarCep(AjaxBehaviorEvent event) {

		try {

			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json/");
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String cep = "";
			StringBuilder json = new StringBuilder();

			while ((cep = reader.readLine()) != null) {
				json.append(cep);
			}

			Pessoa gson = new Gson().fromJson(json.toString(), Pessoa.class);
			pessoa.setCep(gson.getCep());
			pessoa.setLogradouro(gson.getLogradouro());
			pessoa.setBairro(gson.getBairro());
			pessoa.setComplemento(gson.getComplemento());
			pessoa.setLocalidade(gson.getLocalidade());
			pessoa.setUf(gson.getUf());

		} catch (Exception e) {
			e.printStackTrace();
			mostrarMensagem("Erro ao consultar CEP!");
		}
	}

	public List<SelectItem> getEstados() {
		estados = estadoRepository.listarEstados();
		return estados;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}

	public void carregaCidades(AjaxBehaviorEvent event) {

		Estado estado = (Estado) ((HtmlSelectOneMenu) event.getSource()).getValue();

		if (estado != null) {
			pessoa.setEstado(estado);
			cidades = cidadeRepository.listarCidades(estado.getId());
		}

	}

	private void mostrarMensagem(String mensagem) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(mensagem);
		context.addMessage(null, message);
	}

	private void processarImagem() throws IOException {
		byte[] imagemByte = getByte(arquivo.getInputStream());
		pessoa.setFoto(imagemByte);

		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));

		int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
		int largura = 200;
		int altura = 200;

		BufferedImage resizedImage = new BufferedImage(largura, altura, type);
		Graphics2D graphics2d = resizedImage.createGraphics();
		graphics2d.drawImage(resizedImage, 0, 0, largura, altura, null);
		graphics2d.dispose();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String extensao = arquivo.getContentType().split("/")[1];
		ImageIO.write(resizedImage, extensao, outputStream);

		String miniatura = "data:" + arquivo.getContentType() + ";base64,"
				+ DatatypeConverter.printBase64Binary(outputStream.toByteArray());
		
		pessoa.setIcone(miniatura);
		pessoa.setExtensao(extensao);
	}

	private byte[] getByte(InputStream stream) throws IOException {
		int lenght;
		int size = 1024;
		byte[] buffer = null;

		if (stream instanceof ByteArrayInputStream) {
			size = stream.available();
			buffer = new byte[size];
			lenght = stream.read(buffer, 0, size);
		} else {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			buffer = new byte[size];

			while ((lenght = stream.read(buffer, 0, size)) != -1) {
				outputStream.write(buffer, 0, lenght);
			}

			buffer = outputStream.toByteArray();
		}

		return buffer;

	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DAOGenerico<Pessoa> getDaoGeneric() {
		return DAOGenerico;
	}

	public void setDaoGeneric(DAOGenerico<Pessoa> DAOGenerico) {
		this.DAOGenerico = DAOGenerico;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public Part getArquivo() {
		return arquivo;
	}

	public void setArquivo(Part arquivo) {
		this.arquivo = arquivo;
	}

	private ExternalContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;
	}

}
