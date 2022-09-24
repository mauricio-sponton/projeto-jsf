package br.com.mbs.projeto_jsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.mbs.projeto_jsf.model.Pessoa;

public interface PessoaRepository {

	Pessoa findByLoginESenha(String login, String senha);
	List<SelectItem> listarEstados();
}
