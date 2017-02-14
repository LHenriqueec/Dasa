package com.iveso.dasa.convert;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.service.ProdutoService;
import com.iveso.dasa.service.ServiceException;

@FacesConverter("produtoConverter")
@ApplicationScoped
public class ProdutoConverter implements Converter, Serializable {
	private static final long serialVersionUID = 1L;
	

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto produto = null;
		if (value != null && value.trim().length() > 0) {
			try {
				ProdutoService service = (ProdutoService) context.getExternalContext().getApplicationMap().get("produtoService");
				produto = service.carregar(Integer.parseInt(value));
			} catch (NumberFormatException | ServiceException e) {
				e.printStackTrace();
			}
		}
		return produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object object) {
		if (object != null) {
			return String.valueOf(((Produto) object).getId());
		}
		return null;
	}

}
