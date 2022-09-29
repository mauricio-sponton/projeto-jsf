package br.com.mbs.projeto_jsf.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mbs.projeto_jsf.model.Cidade;
import br.com.mbs.projeto_jsf.repository.CidadeRepository;

@FacesConverter(forClass = Cidade.class, value = "cidadeConverter")
public class CidadeConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String idCidade) {
		CidadeRepository repository = CDI.current().select(CidadeRepository.class).get();
		Cidade cidade = repository.findById(Long.parseLong(idCidade));

		return cidade;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object cidade) {

		if (cidade == null) {
			return null;
		}

		if (cidade instanceof Cidade) {
			return ((Cidade) cidade).getId().toString();
		}

		return cidade.toString();
	}

}
