package br.com.mbs.projeto_jsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Estado;
import br.com.mbs.projeto_jsf.util.JPAUtil;

public class EstadoRepositoryImpl implements EstadoRepository{

	@Override
	public List<SelectItem> listarEstados() {
		
		EntityManager manager = JPAUtil.getEntityManager();
		List<SelectItem> items = new ArrayList<>();
		
		List<Estado> estados = manager.createQuery("select e from Estado e").getResultList();	
		
		for (Estado estado : estados) {
			items.add(new SelectItem(estado.getId(), estado.getNome()));
		}
		return items;
	}

	@Override
	public Estado findById(Long estadoId) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		return manager.find(Estado.class, estadoId);
	}
}
