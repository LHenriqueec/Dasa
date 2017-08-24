package br.com.iveso.dasa.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.Produto;


public class IndexService implements Serializable {
	private static final long serialVersionUID = 1L;

	
	public List<Item> todosItens() {
		//TODO: Buscar itens do BD
		List<Item> itens = new ArrayList<>();
		Item item1 = new ItemNota(new Produto("10", "picole limao"), 254);
		Item item2 = new ItemNota(new Produto("12", "picole morango"), 237);
		
		itens.add(item1);
		itens.add(item2);
		
		return itens;
	}
}
