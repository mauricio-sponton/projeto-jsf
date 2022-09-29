package br.com.mbs.projeto_jsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Estado;

@Named
public class EstadoRepositoryImpl implements EstadoRepository, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Override
	public List<SelectItem> listarEstados() {

		List<SelectItem> items = new ArrayList<>();

		List<Estado> estados = entityManager.createQuery("select e from Estado e").getResultList();

		for (Estado estado : estados) {
			items.add(new SelectItem(estado, estado.getNome()));
		}
		return items;
	}

	@Override
	public Estado findById(Long estadoId) {
		return entityManager.find(Estado.class, estadoId);
	}
}
