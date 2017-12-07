package br.com.iveso.dasa.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import br.com.iveso.dasa.entity.ItemRecibo;

public class Exportador {

	public void paraCSV(List<ItemRecibo> itens) {
		try(PrintStream ps = new PrintStream("/tmp/arquivo.csv")) {
			
			ps.println("NOTA,CLIENTE,PRODUTO,QUANTIDADE,DATA,RECIBO");
			itens.forEach(item -> {
				ps.println(String.format("%s,%s,%s,%s,%s",
						item.getRecibo().getCliente().getNome(),
						item.getProduto().getNome(),
						item.getQuantidade(),
						item.getRecibo().getData(),
						item.getRecibo().getNumero()));
			});
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
