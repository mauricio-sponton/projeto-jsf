package br.com.mbs.projeto_jsf.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.mbs.projeto_jsf.dao.DAOGenerico;
import br.com.mbs.projeto_jsf.model.Lancamento;
import br.com.mbs.projeto_jsf.repository.LancamentoRepository;

import javax.faces.view.ViewScoped;

@ViewScoped
@Named(value = "relatorioBean")
public class RelatorioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private LancamentoRepository lancamentoRepository;

	@Inject
	private DAOGenerico<Lancamento> daoGenerico;

	private List<Lancamento> lancamentos = new ArrayList<>();
	private Date dataInicial;
	private Date dataFinal;
	private String numeroNotaFiscal;

	public void buscar() {
		lancamentos = daoGenerico.getListEntity(Lancamento.class);
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNumeroNotaFiscal() {
		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(String numeroNotaFiscal) {
		this.numeroNotaFiscal = numeroNotaFiscal;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
}
