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
	public List<Lancamento> relatorio(String notaFiscal, String dataInicial, String dataFinal) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("select l from Lancamento l where 1 = 1");
		
		if(!notaFiscal.isEmpty()) {
			builder.append(" and l.notaFiscal = " + notaFiscal);
		}
		
		if(dataInicial != null) {
			builder.append(" and l.dataInicial >= '" + dataInicial + "'");
		}
		
		if(dataFinal != null) {
			builder.append(" and l.dataFinal <= '" + dataFinal + "'");
		}
		
		return entityManager.createQuery(builder.toString()).getResultList();
	}

}
