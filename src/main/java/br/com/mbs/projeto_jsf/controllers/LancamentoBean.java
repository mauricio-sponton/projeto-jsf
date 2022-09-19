package br.com.mbs.projeto_jsf.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.mbs.projeto_jsf.dao.DAOGenerico;
import br.com.mbs.projeto_jsf.model.Lancamento;
import br.com.mbs.projeto_jsf.model.Pessoa;

@ViewScoped
@ManagedBean(name = "lancamentoBean")
public class LancamentoBean {

	private Lancamento lancamento = new Lancamento();
	private DAOGenerico<Lancamento> daoGenerico = new DAOGenerico<Lancamento>();
	private List<Lancamento> lancamentos = new ArrayList<>();
	
	public String salvar() {
		ExternalContext externalContext = getContext();
		Pessoa pessoaLogada = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		lancamento.setUsuario(pessoaLogada);
		lancamento = daoGenerico.merge(lancamento);
		
		return "";
	}
	
	public String novo() {
		return "";
	}
	
	public String remove() {
		return "";
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
	
	private ExternalContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		return externalContext;
	}

}
