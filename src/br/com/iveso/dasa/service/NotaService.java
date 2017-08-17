package br.com.iveso.dasa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.entity.Item;
import br.com.iveso.dasa.entity.Produto;

public class NotaService {
	
	public List<Item> totalPorItem(List<Item> itens) {
		List<Item> itensFiltrados =  new ArrayList<>();
		
		itens.stream().map(Item::getProduto).distinct().forEach(produto -> {
			int quantidade = itens.stream().filter(item -> item.getProduto().equals(produto)).mapToInt(Item::getQuantidade).sum();
			
			itensFiltrados.add(new Item(produto, quantidade));
		});
		
		return itensFiltrados;
	}

	public int totalItensDiferentes(List<Item> itens) {
		List<Produto> produtos = itens.stream().map(Item::getProduto).distinct().collect(Collectors.toList());
		return produtos.size();
	}
	
	public int total(List<Item> itens) {
		int quantidade = 0;
		quantidade = itens.stream().mapToInt(Item::getQuantidade).sum();
		return quantidade;
	}
}
