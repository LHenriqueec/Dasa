package br.com.iveso.dasa.util;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import br.com.iveso.dasa.entity.ItemRecibo;
import br.com.iveso.dasa.entity.Recibo;

/**
 * Casse de utilidades para gerar os Recibos em PDF
 * 
 * @author Luiz Henrique
 *
 */
public class PdfUtil {

	private static final String DEST = "/tmp/recibo.pdf";
	private PdfWriter write;
	private PdfDocument pdf;
	private SimpleDateFormat format;
	private Locale pt;

	public PdfUtil() {
		try {
			write = new PdfWriter(DEST);
			pdf = new PdfDocument(write);
			format = new SimpleDateFormat("dd/MM/yyyy");
			pt = new Locale("pt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void gerarComodato() {
		try (Document doc = new Document(pdf)) {
			Paragraph titulo = new Paragraph("CONTRATO DE COMODATO DE FREEZERS");
			titulo.setTextAlignment(TextAlignment.CENTER)
			.setFontSize(12)
			.setBold()
			.setMarginBottom(40)
			.setMarginTop(40);
			doc.add(titulo);
			Image logo = new Image(ImageDataFactory.create("/home/luiz/Imagens/logo.png"));
			logo.setWidth(90)
			.setHeight(90)
			.setFixedPosition(40, 710);
			doc.add(logo);

			Paragraph cliente = new Paragraph("Documento particular de comodato, que entre si fazem de um lado, ")
					.add(new Text("NOME DO CLIENTE").setBold()).add(" sediada em ")
					.add(new Text("ENDEREÇO DO CLIENTE - " + "BAIRRO, CEP: 70346-030").setBold())
					.add(" inscrita no CNPJ ").add(new Text("CNPJ DO CLIENTE").setBold())
					.add(", doravante denominada \"COMODATÁRIO\", e de outro, ")
					.add(new Text("BELLUNO COMÉRCIO E DISTRIBUIÇÃO LTDA").setBold())
					.add(", com sede na Rua Dona Layr Costa Rego 71, Jardim Periperi, "
							+ "São Paulo, SP, inscrita no CNPJ/MF sob o número 11.449.454/0001-60 ora denominado \"COMODANTE\", "
							+ "mediante as seguintes cláusulas e condições:");
			formatFonts(cliente);
			doc.add(cliente);

			Paragraph freezer = new Paragraph(new Text("CLÁUSULA PRIMEIRA:").setBold())
					.add(" A \"COMODANTE\" é legítima proprietária dos equipamentos denominada \'Freezer Modelos: ")
					.add(new Text("MODELO FREEZER").setBold()).add(" Patrimônios: ").add(new Text("PC.").setBold());
			formatFonts(freezer);
			doc.add(freezer);

			Paragraph pg1 = new Paragraph(new Text("CLÁUSULA SEGUNDA: ").setBold()).add(
					"Por este instrumento, e na melhor forma de direito, a “COMODANTE” cede ao \"COMODATÁRIO\" em regime de comodato e "
							+ "em caráter estritamente comercial, o equipamento mencionado na Cláusula Primeira, obrigando-se o "
							+ "\"COMODATÁRIO\" a guardar e conservar o equipamento, zelando pelo mesmo como se seu fosse.");
			formatFonts(pg1);
			doc.add(pg1);

			Paragraph pg1_1 = new Paragraph(new Text("Parágrafo 1: ").setBold()).add(
					"O equipamento ora cedido deverá ser utilizado pelo \"COMODATÁRIO\" exclusivamente para os produtos distribuídos "
							+ "pelo \"COMODANTE\". O COMODANTE isenta-se de problemas causados no equipamento ou nos produtos por falta "
							+ "de energia ou \"mau\" uso do mesmo.");
			pg1_1.setPaddingLeft(80);
			formatFonts(pg1_1);
			doc.add(pg1_1);

			Paragraph pg1_2 = new Paragraph(new Text("Parágrafo 2: ").setBold()).add(
					"O Presente Comodato de Equipamento fica desde já atrelado a compra mínima de produtos do COMODANTE que será definida "
							+ "como meta mensal. Esta meta será definida posteriormente em comum acordo entre COMODANTE e COMODATÁRIO.");
			pg1_2.setPaddingLeft(80);
			formatFonts(pg1_2);
			doc.add(pg1_2);

			Paragraph pg2 = new Paragraph(new Text("CLÁUSULA TERCEIRA: ").setBold()).add(
					"O prazo do presente contrato é indeterminado, ficando convencionado, no entanto, que não havendo interesse "
							+ "comercial por uma das partes, a mesma poderá rescindir o mesmo mediante comunicação à outra com 05 "
							+ "(cinco) dias de antecedência para a retirada do equipamento.");
			formatFonts(pg2);
			doc.add(pg2);

			Paragraph pg3 = new Paragraph(new Text("CLÁUSULA QUARTA: ").setBold()).add(
					"A \"COMODANTE\" poderá a qualquer momento proceder a vistoria do equipamento, bem como dos produtos já guardados "
							+ "pelo \"COMODATÁRIO\".");
			formatFonts(pg3);
			doc.add(pg3);

			Paragraph pg3_unico = new Paragraph(new Text("Parágrafo Único: ").setBold()).add(
					"O Equipamento cedido deverá ser instalado em uma tomada única evitando assim problemas que acarretem em quebra do "
							+ "equipamento e/ou perda da mercadoria.");
			pg3_unico.setPaddingLeft(80);
			formatFonts(pg3_unico);
			doc.add(pg3_unico);

			Paragraph pg4 = new Paragraph(new Text("CLÁUSULA QUINTA: ").setBold()).add(
					"No caso de a rescisão ocorrer por parte da \"COMODANTE\" e havendo recusa do “COMODATÁRIO” na devolução do "
							+ "equipamento, fica assegurado à \"COMODANTE\" o direito de obter a restituição mediante ação de "
							+ "reintegração de posse ")
					.add(new Text("\"Initios Litis\"").setItalic()).add(" sem audiência do \"COMODATÁRIO\".");
			formatFonts(pg4);
			doc.add(pg4);

			Paragraph pg4_unico = new Paragraph(new Text("Parágrafo Único: ").setBold()).add(
					"A rescisão do presente comodato não implicará em qualquer justificação quanto ao motivo determinante, "
							+ "procedendo-se simplesmente, na forma estipulada na Cláusula Terceira.");
			pg4_unico.setPaddingLeft(80);
			formatFonts(pg4_unico);
			doc.add(pg4_unico);

			Paragraph pg5 = new Paragraph(new Text("CLÁUSULA SEXTA: ").setBold()).add(
					"O comodato ora efetuado não facultará o \"COMODATÁRIO\", por ocasião da rescisão, qualquer direito de "
							+ "posse ou indenização.");
			formatFonts(pg5);
			doc.add(pg5);

			Paragraph pg6 = new Paragraph(new Text("CLÁUSULA SÉTIMA: ").setBold()).add(
					" O \"COMODATÁRIO\" pelo comodato que lhe é concedido não se obriga ao pagamento de qualquer quantia, "
							+ "correndo, no entanto a suas expensas, todas as despesas feitas com o uso e gozo do equipamento que, "
							+ "em hipótese alguma, poderá ser cobrado da \"COMODANTE\".");
			formatFonts(pg6);
			doc.add(pg6);

			Paragraph pg7 = new Paragraph(new Text("CLÁUSULA OITAVA: ").setBold()).add(
					"O \"COMODATÁRIO\" não poderá transferir ou ceder a qualquer título as vantagens deste contrato sem a prévia "
							+ "anuência por escrito da \"COMODANTE\", bem como responsabilizar-se á o \"COMODATÁRIO\" pelo equipamento "
							+ "até a sua devolução.");
			formatFonts(pg7);
			doc.add(pg7);

			Paragraph pg8 = new Paragraph(
					"Por estarem de acordo, assinam o presente em duas vias de igual teor na presença das "
							+ "testemunhas abaixo.");
			formatFonts(pg8);
			doc.add(pg8);

			Paragraph assinCliente = new Paragraph(new Text("BELLUNO COMÉRCIO E DISTRIBUIÇÃO LTDA").setBold())
					.add(" ____________________________________");
			assinCliente.setFontSize(11)
			.setPaddings(20, 0, 20, 0)
			.setTextAlignment(TextAlignment.LEFT);
			doc.add(assinCliente);

			Paragraph assinBelluno = new Paragraph(new Text("BELLUNO COMÉRCIO E DISTRIBUIÇÃO LTDA").setBold())
					.add(" ____________________________________");
			assinBelluno.setFontSize(11)
			.setPaddings(0, 0, 20, 0)
			.setTextAlignment(TextAlignment.LEFT);
			doc.add(assinBelluno);

			LocalDate dataEmissao = LocalDate.now();
			Paragraph data = new Paragraph("Brasília, ").add(String.valueOf(dataEmissao.getDayOfMonth()) + " ")
					.add(dataEmissao.getMonth().getDisplayName(TextStyle.FULL, pt).toLowerCase()).add(" de ")
					.add(String.valueOf(dataEmissao.getYear())).add(".");
			data.setTextAlignment(TextAlignment.LEFT);
			doc.add(data);

			formatFonts(cliente);
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private void formatFonts(Paragraph paragraph) {
		paragraph.setFontSize(12)
		.setPaddingBottom(10)
		.setTextAlignment(TextAlignment.JUSTIFIED);
	}

	/**
	 * Gera o PDF dos Recibos
	 * 
	 * @param recibos
	 *            Lista de Recibos que seram gerados em PDF
	 */
	public void gerarPdf(List<Recibo> recibos) {
		Iterator<Recibo> iterator = recibos.iterator();

		try (Document doc = new Document(pdf, PageSize.A4)) {

			// doc = new Document(pdf, ps);

			do {
				Recibo recibo = iterator.next();
				SolidBorder border = new SolidBorder(1);
				border.setColor(Color.GRAY);

				Div dados = new Div();
				dados.setHeight(160).setBorder(border);

				// LEFT
				Div leftDiv = new Div();
				leftDiv.setFixedPosition(40, 652, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(20);

				leftDiv.add(new Paragraph("Número").setBold().setFontSize(12).setMargin(0));
				leftDiv.add(new Paragraph(recibo.getNumero()).setFontSize(12).setMargin(0));

				leftDiv.add(new Paragraph("Data").setBold().setFontSize(12).setMargin(0));
				leftDiv.add(new Paragraph(format.format(recibo.getData())).setFontSize(12).setMargin(0));

				// MIDDLE
				Div middleDiv = new Div();
				middleDiv.setTextAlignment(TextAlignment.CENTER).setFixedPosition(150, 652, 0).setWidthPercent(42);

				middleDiv.add(new Paragraph("CNPJ:").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(new Paragraph(recibo.getCliente().getCnpj()).setFontSize(12).setMargin(0));

				middleDiv.add(new Paragraph("Nome:").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(new Paragraph(recibo.getCliente().getNome().toUpperCase()).setFontSize(12).setMargin(0));

				middleDiv.add(new Paragraph("Responsável").setBold().setFontSize(12).setMargin(0));
				middleDiv.add(new Paragraph(recibo.getCliente().getContato().getResponsavel().toUpperCase())
						.setFontSize(12).setMargin(0));

				// RIGHT
				Div rigthDiv = new Div();
				rigthDiv.setFixedPosition(380, 644, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(33);

				rigthDiv.add(new Paragraph("Cidade").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(new Paragraph(recibo.getCliente().getEndereco().getCidade().toUpperCase()).setFontSize(12)
						.setMargin(0));

				rigthDiv.add(new Paragraph("Bairro").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(new Paragraph(recibo.getCliente().getEndereco().getBairro().toUpperCase()).setFontSize(12)
						.setMargin(0));

				rigthDiv.add(new Paragraph("Endereço").setBold().setFontSize(12).setMargin(0));
				rigthDiv.add(
						new Paragraph(recibo.getCliente().getEndereco().getLogradouro().toUpperCase()).setFontSize(12))
						.setMargin(0);

				dados.add(leftDiv);
				dados.add(middleDiv);
				dados.add(rigthDiv);

				Table table = new Table(new float[] { 1, 99, 1 });
				table.setWidthPercent(100).setBorder(border);

				process(table, recibo, border);

				Div divTable = new Div();
				divTable.setMarginTop(30);

				Table tableDetail = new Table(new float[] { 1, 1 });
				tableDetail.setWidthPercent(100)
						.addHeaderCell(new Cell().setBorder(border).add(
								new Paragraph("Recibo:").setFontSize(12).setBold().add(new Text(recibo.getNumero()))))
						.addHeaderCell(new Cell().setBorder(border)
								.add(new Paragraph(format.format(recibo.getData())).setFontSize(12)
										.setTextAlignment(TextAlignment.RIGHT)))
						.addCell(new Cell(1, 2).setHeight(12).setTextAlignment(TextAlignment.LEFT)
								.add(new Paragraph("Observações:")))
						.addCell(new Cell(1, 2).setHeight(12)).addCell(new Cell(1, 2).setHeight(12))
						.addCell(new Cell(1, 2).setHeight(12)).addCell(new Cell(1, 2).setHeight(12))
						.addCell(new Cell(1, 2).setHeight(12));

				divTable.add(tableDetail);

				Div divAssign = new Div();
				divAssign.setMarginTop(25).setTextAlignment(TextAlignment.CENTER);

				divAssign.add(new Paragraph("______________________________________________"));
				divAssign.add(new Paragraph("Assinatura").setFontSize(12));

				Div reciboDiv = new Div();
				reciboDiv.setMinHeight(770);

				reciboDiv
						.add(new Paragraph("RECIBO").setTextAlignment(TextAlignment.CENTER).setFontColor(Color.GRAY)
								.setBackgroundColor(Color.LIGHT_GRAY).setBold().setFontSize(18))
						.add(dados).add(table).add(divTable).add(divAssign);

				doc.add(reciboDiv);

			} while (iterator.hasNext());
		}
	}

	/**
	 * Processa uma Tabela com todos os itens do Recibo
	 * 
	 * @param table
	 *            Tabela de itens do Recibo
	 * @param recibo
	 *            Recibo que terá os itens apresentados
	 * @param border
	 *            Estilo da borda que será utilizada
	 */
	private void process(Table table, Recibo recibo, Border border) {
		List<ItemRecibo> itens = recibo.getItens();
		int total = 0;

		table.addHeaderCell(new Cell(1, 3).add(new Paragraph("Produtos")).setFontSize(12).setBold()
				.setFontColor(Color.GRAY).setBackgroundColor(Color.LIGHT_GRAY));

		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Código").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Produto").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Quantidade").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER)));

		for (ItemRecibo item : itens) {
			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border).add(
					new Paragraph(item.getProduto().getCodigo()).setFontSize(12).setTextAlignment(TextAlignment.LEFT)));

			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border).add(
					new Paragraph(item.getProduto().getNome()).setFontSize(12).setTextAlignment(TextAlignment.LEFT)));

			table.addCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
					.add(new Paragraph(String.valueOf(item.getQuantidade())).setFontSize(12)
							.setTextAlignment(TextAlignment.CENTER)));

			total += item.getQuantidade();
		}

		table.addFooterCell(
				new Cell(1, 2).add(new Paragraph("Total").setBold().setFontSize(12)).setBorder(Border.NO_BORDER)
						.setFontSize(12).setBold().setFontColor(Color.GRAY).setBackgroundColor(Color.LIGHT_GRAY));
		table.addFooterCell(new Cell()
				.add(new Paragraph(String.valueOf(total)).setBold().setFontSize(12)
						.setTextAlignment(TextAlignment.CENTER))
				.setBorder(Border.NO_BORDER).setFontSize(12).setBold().setFontColor(Color.GRAY)
				.setBackgroundColor(Color.LIGHT_GRAY));
	}
}
