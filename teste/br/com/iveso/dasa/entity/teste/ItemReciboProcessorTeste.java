package br.com.iveso.dasa.entity.teste;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;

public class ItemReciboProcessorTeste {

	private static List<Nota> notas;
	
	static {
		notas = new ArrayList<>();
		Nota nota = new Nota("123");
		nota.getItens().add(new ItemNota(new Produto("0010", "PICOLE TESTE"), 50, nota));
		nota.getItens().add(new ItemNota(new Produto("0012", "PICOLE TESTE"), 50, nota));
		notas.add(nota);
		
		nota = new Nota("456");
		nota.getItens().add(new ItemNota(new Produto("0010", "PICOLE TESTE"), 50, nota));
		nota.getItens().add(new ItemNota(new Produto("0012", "PICOLE TESTE"), 50, nota));
		notas.add(nota);
	}
	
	public static void alterarItens(Recibo recibo, List<ItemRecibo> itensNovos) {
		List<ItemRecibo> itens = new ArrayList<>();
		itensNovos.forEach(newItem -> {
			List<ItemRecibo> itensRecibo = filtrarProdutoRecibo(newItem.getProduto(), recibo);
			itens.addAll(atualizarItens(itensRecibo, newItem));
		});
		
		recibo.setItens(itens);
	}
	
	public static Nota getNota(String numero) {
		return notas.stream().filter(nota -> nota.getNumero().equals(numero)).findFirst().get();
	}
	
	private static List<ItemRecibo> atualizarItens(List<ItemRecibo> itens, ItemRecibo newItem) {
		int totalAtualRecibo = calcularTotalItens(itens);
		
		List<ItemRecibo> itensNovos = new ArrayList<>();
		
		itens.forEach(itemRecibo -> {
			if(newItem.getQuantidade() < totalAtualRecibo) {
				int quantidade = totalAtualRecibo - newItem.getQuantidade();
				itensNovos.addAll(debitarQuantidade(quantidade, itemRecibo));
			} else {
				int quantidade = newItem.getQuantidade() - totalAtualRecibo;
				itensNovos.addAll(creditarQuantidade(quantidade, itemRecibo));
			}
		});
	
		return itensNovos;
	}
	
	private static List<ItemRecibo> creditarQuantidade(int quantidade, ItemRecibo item) {
		// Buscar ItemNota do Banco
		ItemNota itemNota = notas.stream().filter(nota -> item.getNota().equals(nota)).findFirst().get()
				.getItens().stream().filter(itemN -> itemN.getProduto().equals(item.getProduto()))
				.findFirst().get();
		
		List<ItemRecibo> itens = new ArrayList<>();
		while(quantidade > 0) {
			if(itemNota.getQuantidade() >= quantidade) {
				itemNota.debitar(quantidade);
				itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade + item.getQuantidade(), itemNota.getNota()));
				quantidade = 0;
			} else {
				itens.add(new ItemRecibo(item.getRecibo(),itemNota.getProduto(), itemNota.getQuantidade(), itemNota.getNota()));
				quantidade -= itemNota.getQuantidade();
				itemNota.setQuantidade(0);
				itemNota = notas.get(1).getItemByCodigoProduto(item.getProduto().getCodigo());
			}
		}
		
		return itens;
	}
	
	private static List<ItemRecibo> debitarQuantidade(int quantidade, ItemRecibo item) {
		// Buscar ItemNota do Banco
		ItemNota itemNota = notas.stream().filter(nota -> item.getNota().equals(nota)).findFirst().get()
				.getItens().stream().filter(itemN -> itemN.getProduto().equals(item.getProduto()))
				.findFirst().get();
		
		itemNota.creditar(quantidade);
		return Arrays.asList(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade, item.getNota()));
	}
	
	private static List<ItemRecibo> filtrarProdutoRecibo(Produto produto, Recibo recibo) {
		return recibo.getItens().stream().filter(item -> produto.equals(item.getProduto()))
				.collect(Collectors.toList());
	}
	
	private static int calcularTotalItens(List<ItemRecibo> itens) {
		return itens.stream().mapToInt(ItemRecibo::getQuantidade).sum();
	}
}









