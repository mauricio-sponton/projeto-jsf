package br.com.mbs.projeto_jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
		return "";
	}
	
	@PostConstruct
	public void carregarPessoas(){
		pessoas = DAOGenerico.getListEntity(Pessoa.class);
	}
	
	public String logar() {
		
		Pessoa pessoaEcontrada = pessoaRepository.findByLoginESenha(pessoa.getLogin(), pessoa.getSenha());
		
		if(pessoaEcontrada != null) {
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaEcontrada.getLogin());
			return "cadastro-pessoa.jsf";
		}
		
		return "index.jsf";
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
	

}
