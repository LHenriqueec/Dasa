package br.com.iveso.dasa.util;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Nota;
import br.com.iveso.dasa.entity.Recibo;

/**
 * Casse de utilidades para gerar os Recibos em PDF
 * @author Luiz Henrique
 *
 */
public class PdfUtil {
	
	private static final String DEST = "/tmp/recibo.pdf";
	private PdfWriter write;
	private PdfDocument pdf;
	private Document doc;
	private SimpleDateFormat format;

	public PdfUtil() {
		try {
			write = new PdfWriter(DEST);
			pdf = new PdfDocument(write);
			doc = new Document(pdf);
			format = new SimpleDateFormat("dd/MM/yyyy");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gera o PDF dos Recibos
	 * @param recibos Lista de Recibos que seram gerados em PDF
	 */
	public void gerarPdf(List<Recibo> recibos) {
		Iterator<Recibo> iterator = recibos.iterator();
		try {
			
			PageSize ps = PageSize.A4;
			doc = new Document(pdf, ps);
			
			do {
				Recibo recibo = iterator.next();
				SolidBorder border = new SolidBorder(1);
				border.setColor(Color.GRAY);
				
				Div dados = new Div();
				dados.setHeight(160).setBorder(border);
				
				Div leftDiv = new Div();
				leftDiv.setFixedPosition(40, 652, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(20);
				
				leftDiv.add(new Paragraph("Número").setBold().setFontSize(12).setMargin(0));
				leftDiv.add(new Paragraph(recibo.getNumero()).setFontSize(12).setMargin(0));
				
				leftDiv.add(new Paragraph("Data").setBold().setFontSize(12).setMargin(0));
				leftDiv.add(new Paragraph(format.format(recibo.getData())).setFontSize(12).setMargin(0));
				
				leftDiv.add(new Paragraph("Notas").setBold().setFontSize(12).setMargin(0));
				exibirNotas(leftDiv, recibo);
				
				// Middle
				Div middleDiv = new Div();
				middleDiv.setTextAlignment(TextAlignment.CENTER).setFixedPosition(150, 652, 0).setWidthPercent(42);
				
				middleDiv.add(new Paragraph("CNPJ:").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(new Paragraph(recibo.getCliente().getCnpj()).setFontSize(12).setMargin(0));
				
				middleDiv.add(new Paragraph("Nome:").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(
						new Paragraph(recibo.getCliente().getNome().toUpperCase()).setFontSize(12).setMargin(0));
				
				middleDiv.add(new Paragraph("Responsável").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(new Paragraph(recibo.getCliente().getContato().getResponsavel().toUpperCase()).setFontSize(12).setMargin(0));
				
				// Right
				Div rigthDiv = new Div();
				rigthDiv.setFixedPosition(380, 644, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(33);
				
				rigthDiv.add(new Paragraph("Cidade").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(new Paragraph(recibo.getCliente().getEndereco().getCidade().toUpperCase()).setFontSize(12).setMargin(0));
				
				rigthDiv.add(new Paragraph("Bairro").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(new Paragraph(recibo.getCliente().getEndereco().getBairro().toUpperCase()).setFontSize(12).setMargin(0));
				
				rigthDiv.add(new Paragraph("Endereço").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(new Paragraph(recibo.getCliente().getEndereco().getLogradouro().toUpperCase()).setFontSize(12)).setMargin(0);
				
				dados.add(leftDiv);
				dados.add(middleDiv);
				dados.add(rigthDiv);
				
				Table table = new Table(new float[] { 1, 99, 1 });
				table.setWidthPercent(100).setBorder(border);
				
				process(table, recibo, border);
				
				Div divTable = new Div();
				divTable.setMarginTop(30);
				
				Table tableDetail = new Table(new float[] {1, 1});
				tableDetail.setWidthPercent(100)
				.addHeaderCell(new Cell().setBorder(border).add(new Paragraph("Recibo:").setFontSize(12).setBold().add(new Text(recibo.getNumero()))))
				.addHeaderCell(new Cell().setBorder(border).add(new Paragraph(format.format(recibo.getData()))
						.setFontSize(12).setTextAlignment(TextAlignment.RIGHT)))
				.addCell(new Cell(1, 2).setHeight(12).setTextAlignment(TextAlignment.LEFT).add(new Paragraph("Observações:")))
				.addCell(new Cell(1, 2).setHeight(12))
				.addCell(new Cell(1, 2).setHeight(12))
				.addCell(new Cell(1, 2).setHeight(12))
				.addCell(new Cell(1, 2).setHeight(12))
				.addCell(new Cell(1, 2).setHeight(12));
				
				divTable.add(tableDetail);
				
				Div divAssign = new Div();
				divAssign.setMarginTop(25)
				.setTextAlignment(TextAlignment.CENTER);
				
				divAssign.add(new Paragraph("______________________________________________"));
				divAssign.add(new Paragraph("Assinatura").setFontSize(12));
				
				Div reciboDiv = new Div();
				reciboDiv.setMinHeight(770);
				
				reciboDiv.add(new Paragraph("RECIBO").setTextAlignment(TextAlignment.CENTER).setFontColor(Color.GRAY)
						.setBackgroundColor(Color.LIGHT_GRAY).setBold().setFontSize(18))
				.add(dados)
				.add(table)
				.add(divTable)
				.add(divAssign);
				
				doc.add(reciboDiv);
				
				
			} while(iterator.hasNext());
		} finally {
			doc.close();
		}
	}
	
	/**
	 * Exibe as Notas do Recibo no PDF
	 * @param div Container que exibe as Notas do Recibo
	 * @param recibo Objeto que terá as Noas exibidas
	 */
	private void exibirNotas(Div div, Recibo recibo) {
		Iterator<Nota> iterator = ReciboUtil.filtrarNotas(recibo).iterator();
		StringBuffer buff = new StringBuffer();
		
		do {
			buff.append(iterator.next().getNumero());
			
			if(iterator.hasNext()) buff.append(", ");
		} while(iterator.hasNext());
		div.add(new Paragraph(buff.toString()).setFontSize(12).setMargin(0));
	}
	
	/**
	 * Processa uma Tabela com todos os itens do Recibo
	 * @param table Tabela de itens do Recibo
	 * @param recibo Recibo que terá os itens apresentados
	 * @param border Estilo da borda que será utilizada
	 */
	private void process(Table table, Recibo recibo, Border border) {
		List<ItemRecibo> itens = ReciboUtil.processarItens(recibo);
		int total = 0;
		
		table.addHeaderCell(new Cell(1, 3).add(new Paragraph("Produtos")).setFontSize(12).setBold()
				.setFontColor(Color.GRAY).setBackgroundColor(Color.LIGHT_GRAY));
		
		
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Código").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Produto").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Quantidade").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER)));
		
		for(ItemRecibo item: itens) {
			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
					.add(new Paragraph(item.getProduto().getCodigo()).setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
			
			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
					.add(new Paragraph(item.getProduto().getNome()).setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
			
			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
					.add(new Paragraph(String.valueOf(item.getQuantidade())).setFontSize(12).setTextAlignment(TextAlignment.CENTER)));
			
			total += item.getQuantidade();
		}
		
		
		table.addFooterCell(new Cell(1, 2).add(new Paragraph("Total").setBold().setFontSize(12))
				.setBorder(Border.NO_BORDER)
				.setFontSize(12)
				.setBold()
				.setFontColor(Color.GRAY)
				.setBackgroundColor(Color.LIGHT_GRAY));
		table.addFooterCell(new Cell().add(new Paragraph(String.valueOf(total)).setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER))
				.setBorder(Border.NO_BORDER)
				.setFontSize(12)
				.setBold()
				.setFontColor(Color.GRAY)
				.setBackgroundColor(Color.LIGHT_GRAY));
	}
}



















