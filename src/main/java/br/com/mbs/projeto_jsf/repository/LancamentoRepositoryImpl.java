package br.com.mbs.projeto_jsf.repository;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Lancamento;
import br.com.mbs.projeto_jsf.util.JPAUtil;

public class LancamentoRepositoryImpl implements LancamentoRepository{

	@Override
	public List<Lancamento> findAll(Long idUsuario) {
		List<Lancamento> lista = null;
		
		EntityManager manager = JPAUtil.getEntityManager();
		lista = manager.createQuery("select l from Lancamento l where l.usuario.id = " + idUsuario).getResultList();
		manager.close();
		
		return lista;
	}

}
