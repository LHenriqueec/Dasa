package br.com.iveso.dasa.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mysql.cj.api.io.Protocol.GetProfilerEventHandlerInstanceFunction;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;

public class ReciboUtil {

	public static List<ItemRecibo> processarItens(Recibo recibo) {
		List<ItemRecibo> processados = new ArrayList<>();
		
		//TODO: Separar itens com produtos iguais
		
		Set<Produto> produtos = getProdutosSemDuplicidade(recibo);
		
		for(Produto produto : produtos) {
			int quantidade = recibo.getItens().stream().filter(item -> item.getProduto().equals(produto))
					.mapToInt(ItemRecibo::getQuantidade).sum();
			processados.add(new ItemRecibo(produto, quantidade));
		}
		
		return processados;
	}
	
	public static Set<Nota> filtrarNotas(Recibo recibo) {
		Set<Nota> notas = new HashSet<>();
		recibo.getItens().forEach(item -> notas.add(item.getNota()));
		return notas;
	}
	
	private static Set<Produto> getProdutosSemDuplicidade(Recibo recibo) {
		Set<Produto> produtos = new HashSet<>();
		
		for(ItemRecibo item : recibo.getItens()) {
			produtos.add(item.getProduto());
		}
		
		return produtos;
	}
}
