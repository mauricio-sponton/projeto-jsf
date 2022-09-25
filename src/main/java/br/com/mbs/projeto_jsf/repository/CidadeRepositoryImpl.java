package br.com.mbs.projeto_jsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Cidade;
import br.com.mbs.projeto_jsf.util.JPAUtil;

public class CidadeRepositoryImpl implements CidadeRepository{

	@Override
	public List<SelectItem> listarCidades(Long estadoId) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		List<SelectItem> items = new ArrayList<>();
		
		List<Cidade> cidades = manager.createQuery("select c from Cidade c where c.estado.id = " + estadoId).getResultList();	
		
		for (Cidade cidade : cidades) {
			items.add(new SelectItem(cidade.getId(), cidade.getNome()));
		}
		
		return items;
	}

}
