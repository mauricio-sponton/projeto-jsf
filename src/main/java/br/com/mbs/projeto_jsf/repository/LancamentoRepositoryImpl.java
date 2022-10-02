package br.com.mbs.projeto_jsf.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Lancamento;

@Named
public class LancamentoRepositoryImpl implements LancamentoRepository, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	
	@Override
	public List<Lancamento> findAll(Long idUsuario) {

		List<Lancamento> lista = entityManager.createQuery("select l from Lancamento l where l.usuario.id = " + idUsuario)
				.getResultList();

		return lista;
	}

	@Override
	public List<Lancamento> relatorio(String notaFiscal, Date dataInicial, Date dataFinal) {
		return null;
	}

}
