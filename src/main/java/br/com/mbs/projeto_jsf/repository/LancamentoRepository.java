package br.com.mbs.projeto_jsf.repository;

import java.util.Date;
import java.util.List;

import br.com.mbs.projeto_jsf.model.Lancamento;

public interface LancamentoRepository {

	List<Lancamento> findAll(Long idUsuario);
	List<Lancamento> relatorio(String notaFiscal, String dataInicialConvertida, String dataFinalConvertida);
}
