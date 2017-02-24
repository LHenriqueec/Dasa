package com.iveso.dasa.teste;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Pedido;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.Recibo;

public class Teste {

	private static Nota nota;
	
	@BeforeClass
	public static void init() {
		nota = new Nota();
		
		
		Produto produto = new Produto();
		produto.setNome("Morango");
		produto.setQuantidade(50);
		
		
		nota.getProdutos().add(produto);
		
		Produto produto2 = new Produto();
		produto2.setNome("Limao");
		produto2.setQuantidade(50);
		
		nota.getProdutos().add(produto2);
		
	}
	
	@Test
	public void atualizando_quantidade_produto_nota() {
		nota.getProdutos().forEach(p -> {
			System.out.println(p.getNome() + "	" + p.getQuantidade());
			
		});
		
		System.out.println("==========");
	}
	
	@Test
	public void gerando_recibo() {
		Recibo recibo = new Recibo();
		
		Pedido pedido = new Pedido();
		pedido.setNota(nota);
		
		
		pedido.inserirProduto(pedido.getNota().getProdutos().get(0));
		pedido.inserirQuantidade(20);
		
		
		Assert.assertEquals(new Integer(30), nota.getProdutos().get(0).getQuantidade());
		System.out.println(nota.getProdutos().get(0).getQuantidade());
		
		recibo.getPedidos().add(pedido);
	}
}
