package br.com.iveso.dasa.processor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.com.iveso.dasa.entity.ItemNota;
import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Produto;
import br.com.iveso.dasa.entity.Recibo;

public class ItemReciboProcessorTeste {

	private List<ItemNota> itensNota;

	public ItemReciboProcessorTeste() {
		itensNota = new ArrayList<>();
		Nota nota = new Nota("123");
		nota.getItens().add(new ItemNota(new Produto("0010", "PICOLE TESTE"), 50, nota));
		nota.getItens().add(new ItemNota(new Produto("0012", "PICOLE TESTE2"), 50, nota));
		itensNota.addAll(nota.getItens());

		nota = new Nota("456");
		nota.getItens().add(new ItemNota(new Produto("0010", "PICOLE TESTE"), 50, nota));
		nota.getItens().add(new ItemNota(new Produto("0012", "PICOLE TESTE2"), 50, nota));
		itensNota.addAll(nota.getItens());
	}

	public void alterarItens(Recibo recibo, List<ItemRecibo> itensNovos) {
		List<ItemRecibo> itens = new ArrayList<>();
		itensNovos.forEach(newItem -> {
			List<ItemRecibo> itensRecibo = filtrarProdutoRecibo(newItem.getProduto(), recibo);
			itens.addAll(atualizarItens(itensRecibo, newItem));
		});

		recibo.setItens(itens);
	}

	public Nota getNota(String numero) {
		return itensNota.stream().filter(itemNota -> itemNota.getNota().getNumero().equals(numero)).findFirst().get()
				.getNota();
	}

	/**
	 * Faz o processamento do novo Item
	 * 
	 * @param itens
	 *            Lista com itens filtrados por {@link Produto}
	 * @param newItem
	 *            Novo Item que será processado
	 * @return Lista com itens processados
	 */
	private List<ItemRecibo> atualizarItens(List<ItemRecibo> itens, ItemRecibo newItem) {
		int quantidadeAtual = calcularTotalItens(itens);
		int quantidadeNova = newItem.getQuantidade();

		List<ItemRecibo> itensNovos = new ArrayList<>();

		// itens.forEach(itemRecibo -> {
		// if(newItem.getQuantidade() < totalAtual) {
		// int quantidade = totalAtual - newItem.getQuantidade();
		// itensNovos.addAll(debitarQuantidade(quantidade, itemRecibo));
		// } else {
		// int quantidade = newItem.getQuantidade() - totalAtual;
		// itensNovos.addAll(creditarQuantidade(quantidade, itemRecibo));
		// }
		// });

		if (quantidadeAtual == quantidadeNova)
			return itens;

		// Debita a diferença
		if (quantidadeAtual > quantidadeNova) {
			int quantidade = quantidadeAtual - quantidadeNova;
			itensNovos.addAll(debitarQuantidade(quantidade, itens));

			// Credita a diferença
		} else {
			int quantidade = quantidadeNova - quantidadeAtual;
			itensNovos.addAll(creditarQuantidade(quantidade, itens));
		}

		return itensNovos;
	}

	/**
	 * Credita a diferença necessária para chegar no novo valor
	 * 
	 * @param quantidade
	 *            Quantidade total que precisa ser creditada
	 * @param itens
	 *            Lista que terá as quantidade creditadas
	 * @return Lista com quantidades corrigidas
	 */
	private List<ItemRecibo> creditarQuantidade(int quantidade, List<ItemRecibo> itens) {
		// Filtra os itensNota que possuem o mesmo produto do itemRecibo
		// Isso será feito pelo banco de Dados
		List<ItemNota> itensNotaFiltrados = itensNota.stream()
				.filter(iNota -> iNota.getProduto().equals(itens.get(0).getProduto())).collect(Collectors.toList());
		// Ordena os itens com o Número das Notas em ordem decrescente
		itens.sort((item1, item2) -> item2.getNota().getNumero().compareTo(item1.getNota().getNumero()));

		// Verificar se a Nota do último item, que é o primeiro da lista, tem saldo
		// suficiente
		ItemRecibo item = itens.get(0);
		ItemNota itemNota = itensNotaFiltrados.stream()
				.filter(iNota -> iNota.getNota().getNumero().equals(item.getNota().getNumero())).findFirst().get();

		if (itemNota.getQuantidade() >= quantidade) {
			itemNota.debitar(quantidade);
			item.creditar(quantidade);
			quantidade = 0;
		} else {
			// Usa apenas itensNota que não estão sendo uzadas pelos Itens do Recibo
			itensNotaFiltrados.remove(itemNota);
			
			Iterator<ItemNota> iterator = itensNotaFiltrados.iterator(); 
			
			quantidade -= itemNota.getQuantidade();
			item.creditar(itemNota.getQuantidade());
			itemNota.setQuantidade(0);
			
			while(quantidade > 0) {
				itemNota = iterator.next();
				
				if(itemNota.getQuantidade() >= quantidade) {
					itemNota.debitar(quantidade);
					itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), quantidade, itemNota.getNota()));
					quantidade = 0;
				} else {
					quantidade -= itemNota.getQuantidade();
					itens.add(new ItemRecibo(item.getRecibo(), item.getProduto(), itemNota.getQuantidade(), itemNota.getNota()));
					itemNota.setQuantidade(0);
				}
			}
		}

		return itens;
	}

	/**
	 * Debita a diferença necessária para chegar no novo valor
	 * 
	 * @param quantidade
	 *            Quantidade total que precisa ser debitada
	 * @param itens
	 *            Lista que terá as quantidade debitadas
	 * @return Lista com quantidades corrigidas
	 */
	private List<ItemRecibo> debitarQuantidade(int quantidade, List<ItemRecibo> itens) {
		List<ItemRecibo> itensAtualizados = new ArrayList<>();

		// Ordena os itens com o Número das Notas em ordem decrescente
		itens.sort((item1, item2) -> item2.getNota().getNumero().compareTo(item1.getNota().getNumero()));
		Iterator<ItemRecibo> iterator = itens.iterator();

		do {
			ItemRecibo item = iterator.next();
			// Buscar ItemNota do Banco
			ItemNota itemNota = itensNota.stream().filter(
					iNota -> iNota.getNota().equals(item.getNota()) && iNota.getProduto().equals(item.getProduto()))
					.findFirst().get();

			if (item.getQuantidade() > quantidade) {
				item.debitar(quantidade);
				itemNota.creditar(quantidade);
				quantidade = 0;

				itensAtualizados.add(item);
			} else {
				itemNota.creditar(item.getQuantidade());
				quantidade -= item.getQuantidade();
			}

		} while (quantidade > 0);

		return itensAtualizados;
	}

	private List<ItemRecibo> filtrarProdutoRecibo(Produto produto, Recibo recibo) {
		return recibo.getItens().stream().filter(item -> produto.equals(item.getProduto()))
				.collect(Collectors.toList());
	}

	private int calcularTotalItens(List<ItemRecibo> itens) {
		return itens.stream().mapToInt(ItemRecibo::getQuantidade).sum();
	}
}
