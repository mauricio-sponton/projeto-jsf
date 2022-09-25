package br.com.mbs.projeto_jsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.mbs.projeto_jsf.model.Cidade;

public interface CidadeRepository {

	List<SelectItem> listarCidades(Long estadoId);
	Cidade findById(Long cidadeId);
}
