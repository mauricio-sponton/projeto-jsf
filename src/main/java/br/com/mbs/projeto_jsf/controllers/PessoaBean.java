package br.com.mbs.projeto_jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.mbs.projeto_jsf.dao.DAOGenerico;
import br.com.mbs.projeto_jsf.model.Pessoa;
import br.com.mbs.projeto_jsf.repository.PessoaRepository;
import br.com.mbs.projeto_jsf.repository.PessoaRepositoryImpl;

@ViewScoped
@ManagedBean(name = "pessoaBean")
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();
	private DAOGenerico<Pessoa> DAOGenerico = new DAOGenerico<Pessoa>();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	private PessoaRepository pessoaRepository = new PessoaRepositoryImpl();

	public String salvar() {
		if(pessoa.getId() != null) {
			mostrarMensagem("Edição efetuada com sucesso!");
		}else {
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
	
	public String remove(){
		DAOGenerico.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMensagem("Usuário excluído!");
		return "";
	}
	
	@PostConstruct
	public void carregarPessoas(){
		pessoas = DAOGenerico.getListEntity(Pessoa.class);
	}
	
	public String logar() {
		
		Pessoa pessoaEcontrada = pessoaRepository.findByLoginESenha(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaEcontrada != null) {
			ExternalContext externalContext = getContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaEcontrada);
			return "cadastro-pessoa.jsf";
		}
		
		return "index.jsf";
	}

	
	public boolean permitirAcesso(String acesso) {
		ExternalContext externalContext = getContext();
		Pessoa pessoaLogada = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		
		return pessoaLogada.getPerfil().equals(acesso);
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
