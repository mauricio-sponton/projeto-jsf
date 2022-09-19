package br.com.mbs.projeto_jsf.repository;

import javax.persistence.EntityManager;

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

}
