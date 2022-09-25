package br.com.mbs.projeto_jsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

public interface CidadeRepository {

	List<SelectItem> listarCidades(Long estadoId);
}
