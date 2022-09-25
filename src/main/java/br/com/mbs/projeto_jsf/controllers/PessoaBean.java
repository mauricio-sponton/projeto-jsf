package br.com.mbs.projeto_jsf.controllers;

import java.io.BufferedReader;
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
import javax.servlet.http.HttpServletRequest;

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

	public String salvar() {
		if (pessoa.getId() != null) {
			mostrarMensagem("Edição efetuada com sucesso!");
		} else {
			mostrarMensagem("Usuário cadastrado com sucesso!");

		}
		pessoa = DAOGenerico.merge(pessoa);
		carregarPessoas();

		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		return "";
	}
	
	public void editar() {
		if(pessoa.getCidade() != null) {
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

	private ExternalContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;
	}

}
