package br.com.mbs.projeto_jsf.repository;

import java.util.List;

import javax.faces.model.SelectItem;

import br.com.mbs.projeto_jsf.model.Estado;

public interface EstadoRepository {

	List<SelectItem> listarEstados();
	Estado findById(Long estadoId);
}
