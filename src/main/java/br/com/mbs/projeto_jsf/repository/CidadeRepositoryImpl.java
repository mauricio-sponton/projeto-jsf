package br.com.mbs.projeto_jsf.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Cidade;

@Named
public class CidadeRepositoryImpl implements CidadeRepository, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;

	@Override
	public List<SelectItem> listarCidades(Long estadoId) {

		List<SelectItem> items = new ArrayList<>();

		List<Cidade> cidades = entityManager.createQuery("select c from Cidade c where c.estado.id = " + estadoId)
				.getResultList();

		for (Cidade cidade : cidades) {
			items.add(new SelectItem(cidade, cidade.getNome()));
		}

		return items;
	}

	@Override
	public Cidade findById(Long cidadeId) {
		return entityManager.find(Cidade.class, cidadeId);
	}

}
