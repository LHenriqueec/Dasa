package com.iveso.dasa.teste;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.iveso.dasa.entity.Nota;
import com.iveso.dasa.entity.Pedido;
import com.iveso.dasa.entity.Produto;
import com.iveso.dasa.entity.ProdutoPedido;
import com.iveso.dasa.entity.Recibo;

public class Teste {

	private static Nota nota;
	private static ProdutoPedido pedido;
	
	@BeforeClass
	public static void init() {
		nota = new Nota();
		
		pedido = new ProdutoPedido();
		Produto produto = new Produto();
		produto.setNome("Morango");
		pedido.setProduto(produto);
		pedido.setQuantidade(50);
		
		nota.getProdutosPedidos().add(pedido);
		
		pedido = new ProdutoPedido();
		Produto produto2 = new Produto();
		produto2.setNome("Limao");
		pedido.setProduto(produto2);
		pedido.setQuantidade(50);
		
		nota.getProdutosPedidos().add(pedido);
		
	}
	
	@Test
	public void atualizando_quantidade_produto_nota() {
		nota.getProdutosPedidos().forEach(p -> {
			System.out.println(p.getProduto().getNome() + "	" + p.getQuantidade());
			
		});
		
		System.out.println("==========");
	}
	
	@Test
	public void gerando_recibo() {
		Recibo recibo = new Recibo();
		
		Pedido pedido = new Pedido();
		pedido.setNota(nota);
		ProdutoPedido ped = new ProdutoPedido();
		ped.setProduto(pedido.getNota().getProdutosPedidos().get(0).getProduto());
		ped.setQuantidade(20);
		
		pedido.setPedidoProduto(ped);
		
		Assert.assertEquals(new Integer(30), nota.getProdutosPedidos().get(0).getQuantidade());
		System.out.println(nota.getProdutosPedidos().get(0).getQuantidade());
		
		recibo.getPedidos().add(pedido);
	}
}
