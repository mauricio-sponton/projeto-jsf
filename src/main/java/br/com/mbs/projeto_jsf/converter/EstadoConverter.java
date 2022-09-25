package br.com.mbs.projeto_jsf.converter;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mbs.projeto_jsf.model.Estado;
import br.com.mbs.projeto_jsf.repository.EstadoRepository;
import br.com.mbs.projeto_jsf.repository.EstadoRepositoryImpl;

@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String idEstado) {
		EstadoRepository repository = new EstadoRepositoryImpl();
		Estado estado = repository.findById(Long.parseLong(idEstado));
		
		return estado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		return ((Estado) estado).getId().toString();
	}

}
