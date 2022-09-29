package br.com.mbs.projeto_jsf.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mbs.projeto_jsf.model.Estado;
import br.com.mbs.projeto_jsf.repository.EstadoRepository;

@FacesConverter(forClass = Estado.class, value = "estadoConverter")
public class EstadoConverter implements Converter, Serializable{
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String idEstado) {
		EstadoRepository repository = CDI.current().select(EstadoRepository.class).get();
		Estado estado = repository.findById(Long.parseLong(idEstado));
		
		return estado;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		if(estado == null) {
			return null;
		}
		
		if(estado instanceof Estado) {
			return ((Estado) estado).getId().toString();
		}
		
		return estado.toString();
	}

}
