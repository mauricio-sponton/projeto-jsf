package br.com.mbs.projeto_jsf.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.mbs.projeto_jsf.dao.DAOGenerico;
import br.com.mbs.projeto_jsf.model.Lancamento;
import br.com.mbs.projeto_jsf.model.Pessoa;
import br.com.mbs.projeto_jsf.repository.LancamentoRepository;

@javax.faces.view.ViewScoped
@Named(value = "lancamentoBean")
public class LancamentoBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Lancamento lancamento = new Lancamento();
	private List<Lancamento> lancamentos = new ArrayList<>();

	@Inject
	private DAOGenerico<Lancamento> daoGenerico;

	@Inject
	private LancamentoRepository lancamentoRepository;

	public String salvar() {
		Pessoa pessoaLogada = getPessoaLogada();
		lancamento.setUsuario(pessoaLogada);
		lancamento = daoGenerico.merge(lancamento);

		listaLancamentos();

		return "";
	}

	public String novo() {
		lancamento = new Lancamento();
		return "";
	}

	public String remove() {
		daoGenerico.deletePorId(lancamento);
		lancamento = new Lancamento();
		listaLancamentos();
		return "";
	}

	@PostConstruct
	private void listaLancamentos() {
		Pessoa pessoaLogada = getPessoaLogada();
		lancamentos = lancamentoRepository.findAll(pessoaLogada.getId());
	}

	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DAOGenerico<Lancamento> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DAOGenerico<Lancamento> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}

	private Pessoa getPessoaLogada() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaLogada = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		return pessoaLogada;
	}

}
