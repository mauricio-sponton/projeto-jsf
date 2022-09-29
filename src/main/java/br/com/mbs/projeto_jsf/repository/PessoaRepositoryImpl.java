package br.com.mbs.projeto_jsf.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import br.com.mbs.projeto_jsf.model.Pessoa;

@Named
public class PessoaRepositoryImpl implements PessoaRepository, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	@Override
	public Pessoa findByLoginESenha(String login, String senha) {

		Pessoa pessoa = (Pessoa) entityManager
				.createQuery("select p from Pessoa p where p.login = '" + login + "' and p.senha = '" + senha + "'")
				.getSingleResult();

		return pessoa;
	}

}
