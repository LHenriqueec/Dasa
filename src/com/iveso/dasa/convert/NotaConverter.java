package com.iveso.dasa.convert;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.service.NotaService;
import com.iveso.dasa.service.ServiceException;

@FacesConverter("notaConverter")
public class NotaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Nota nota = null;
		try {
			if (value != null && value.trim().length() > 0) {
				NotaService service = (NotaService) context.getExternalContext().getApplicationMap().get("notaService");
				nota = service.carregarByNumeroNota(value);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return nota;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object obj) {
		if (obj != null) {
			return ((Nota) obj).getNumeroNota();
		}
		return null;
	}

}
