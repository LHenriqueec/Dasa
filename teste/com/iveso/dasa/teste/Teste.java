package com.iveso.dasa.teste;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.iveso.dasa.entity.Produto;

public class Teste {
	
	private static EntityManagerFactory factory;
	private EntityManager em;
	private List<Produto> produtosDB;
	
	@BeforeClass
	public static void init() {
		factory = Persistence.createEntityManagerFactory("Dasa"); 
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void carregar_produtos() {
		em = factory.createEntityManager();
		produtosDB = em.createQuery("SELECT p from Produto p").getResultList();
		
		
		List<Produto> produtos = new ArrayList<>();
		Produto p = produtosDB.get(0);
		produtos.add(p);
		
		
		produtos.forEach(produto -> System.out.println(produto.getCodigo() + ": " + produto.getNome()));
		
		
	}
}
