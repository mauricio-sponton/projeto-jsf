package br.com.mbs.projeto_jsf.repository;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Estado;
import br.com.mbs.projeto_jsf.model.Pessoa;
import br.com.mbs.projeto_jsf.util.JPAUtil;

public class PessoaRepositoryImpl implements PessoaRepository {

	@Override
	public Pessoa findByLoginESenha(String login, String senha) {

		EntityManager manager = JPAUtil.getEntityManager();

		Pessoa pessoa = (Pessoa) manager
				.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'")
				.getSingleResult();

		manager.close();

		return pessoa;
	}

	@Override
	public List<SelectItem> listarEstados() {
		
		EntityManager manager = JPAUtil.getEntityManager();
		List<SelectItem> items = new ArrayList<>();
		
		List<Estado> estados = manager.createQuery("select e from Estado e").getResultList();	
		
		for (Estado estado : estados) {
			items.add(new SelectItem(estado, estado.getNome()));
		}
		return items;
	}

}
