package br.com.iveso.dasa.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.service.IndexService;

@Named
@RequestScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Item> itens;
	
	@Inject
	private IndexService service;
	
	@PostConstruct
	private void started() {
		itens = service.todosItens();
	}
	
	public int total() {
		return itens.stream().mapToInt(Item::getQuantidade).sum();
	}
	
	public List<Item> getItens() {
		return itens;
	}

}
