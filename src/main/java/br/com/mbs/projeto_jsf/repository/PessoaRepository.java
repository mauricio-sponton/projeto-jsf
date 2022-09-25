package br.com.mbs.projeto_jsf.repository;

import br.com.mbs.projeto_jsf.model.Pessoa;

public interface PessoaRepository {

	Pessoa findByLoginESenha(String login, String senha);
	
}
