package br.com.iveso.dasa.util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.List;

import br.com.iveso.dasa.entity.ItemRecibo;

public class Exportador {
	
	private SimpleDateFormat sdf;
	
	public Exportador() {
		sdf = new SimpleDateFormat("dd/MM/yyyy");
	}

	public boolean paraCSV(List<ItemRecibo> itens) {
		try(PrintStream ps = new PrintStream("/tmp/arquivo.csv")) {
			
			ps.println("CLIENTE,PRODUTO,QUANTIDADE,DATA,RECIBO");
			itens.forEach(item -> {
				ps.println(String.format("%s,%s,%s,%s,%s",
						item.getRecibo().getCliente().getNome(),
						item.getProduto().getNome(),
						item.getQuantidade(),
						sdf.format(item.getRecibo().getData()),
						item.getRecibo().getNumero()));
			});
			return true;
			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
}
