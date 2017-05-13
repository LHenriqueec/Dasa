package com.iveso.dasa.convert;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.iveso.dasa.entity.Cliente;
import com.iveso.dasa.service.ClienteService;
import com.iveso.dasa.service.ServiceException;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente cliente = null;
		if (value != null && value.trim().length() > 0) {
			try {
				ClienteService service = (ClienteService) context.getExternalContext().getApplicationMap().get("clienteService");
				cliente = service.getClienteById(Integer.parseInt(value));
			} catch (NumberFormatException | ServiceException e) {
				e.printStackTrace();
			}
		}
		return cliente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if (object != null) {
			return String.valueOf(((Cliente) object).getCnpj());
		}
		return null;
	}

}
