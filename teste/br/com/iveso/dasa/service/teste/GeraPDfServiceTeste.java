package br.com.iveso.dasa.service.teste;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;

import br.com.iveso.dasa.dao.DAOFactory;
import br.com.iveso.dasa.dao.ReciboDAO;
import br.com.iveso.dasa.entity.Recibo;
import br.com.iveso.dasa.util.PdfUtil;

public class GeraPDfServiceTeste {

	private static final String DEST = "/tmp/recibo.pdf";
	private PdfWriter write;
	private PdfDocument pdf;
	private Document doc;
	private Recibo recibo;
	private ReciboDAO dao;

	@Before
	public void init() throws Exception {
		write = new PdfWriter(DEST);
		pdf = new PdfDocument(write);
		doc = new Document(pdf);
	}

	@Test
	public void gera_arquivo_pdf() throws Exception {
		doc.add(new Paragraph("Luiz Henrique"));
		doc.close();
	}

	@Test
	public void gera_arquivo_pdf_com_lista_e_fonte() throws Exception {
		// Alterando a fonte do pdf para TIMES NEW ROMAN
		PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

		doc.add(new Paragraph("iText is:").setFont(font));

		List list = new List().setSymbolIndent(12).setListSymbol("\u2022").setFont(font);

		list.add(new ListItem("Never gonna give you up")).add(new ListItem("Never gonna let you down"))
				.add(new ListItem("Never gonna run around and desert you"))
				.add(new ListItem("Never gonna make you cry")).add(new ListItem("Never gonna say goodbye"))
				.add(new ListItem("Never gonna tell a lie and hurt you"));

		doc.add(list);
		doc.close();
	}
	
	@Test
	public void metodo_gerar_recibo() throws Exception {
		dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
		recibo = dao.load("17003");
		
		PdfUtil pdf = new PdfUtil();
		
		pdf.gerarPdf(Arrays.asList(recibo));
	}

	@Test
	public void codigo_gerar_recibo() throws Exception {
		// dao = DAOFactory.getInstance().getDAO(ReciboDAO.class);
		// recibo = dao.load("17001");

		// Corrigir cabeçalho Recibo pdf
		PageSize ps = PageSize.A4;
		doc = new Document(pdf, ps);
		

		SolidBorder border = new SolidBorder(1);
		border.setColor(Color.GRAY);

		Div dados = new Div();
		dados.setHeight(160).setBorder(border);

		Div leftDiv = new Div();
		leftDiv.setFixedPosition(40, 652, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(20);

		leftDiv.add(new Paragraph("Número").setBold().setFontSize(12).setMargin(0));
		leftDiv.add(new Paragraph("17001").setFontSize(12).setMargin(0));

		leftDiv.add(new Paragraph("Data").setBold().setFontSize(12).setMargin(0));
		leftDiv.add(new Paragraph("012/09/2017").setFontSize(12).setMargin(0));

		leftDiv.add(new Paragraph("Responsável").setBold().setFontSize(12).setMargin(0));
		leftDiv.add(new Paragraph("Wellington").setFontSize(12).setMargin(0));

		// Middle
		Div middleDiv = new Div();
		middleDiv.setTextAlignment(TextAlignment.CENTER).setFixedPosition(150, 652, 0).setWidthPercent(42);

		middleDiv.add(new Paragraph("CNPJ:").setBold().setFontSize(12).setMargin(0));
		middleDiv.add(new Paragraph("21.339.044/000112").setFontSize(12).setMargin(0));

		middleDiv.add(new Paragraph("Nome:").setBold().setFontSize(12).setMargin(0));
		middleDiv.add(
				new Paragraph("Ultra Frios Comercio de Produtos Industrializados Ltda").setFontSize(12).setMargin(0));

		// Right
		Div rigthDiv = new Div();
		rigthDiv.setFixedPosition(380, 632, 0).setTextAlignment(TextAlignment.CENTER).setWidthPercent(33);

		rigthDiv.add(new Paragraph("Cidade").setBold().setFontSize(12).setMargin(0));
		rigthDiv.add(new Paragraph("Brasília").setFontSize(12).setMargin(0));

		rigthDiv.add(new Paragraph("Bairro").setBold().setFontSize(12).setMargin(0));
		rigthDiv.add(new Paragraph("Vicente Pires").setFontSize(12).setMargin(0));

		rigthDiv.add(new Paragraph("Endereço").setBold().setFontSize(12).setMargin(0));
		rigthDiv.add(new Paragraph("SHVP Chácara 134 Galpões 02 e 03").setFontSize(12));

		dados.add(leftDiv);
		dados.add(middleDiv);
		dados.add(rigthDiv);

		Table table = new Table(new float[] { 1, 99, 1 });
		table.setWidthPercent(100).setBorder(border);

		// TODO: Transformar em um método
		table.addHeaderCell(new Cell(1, 3).add(new Paragraph("Produtos")).setFontSize(12).setBold()
				.setFontColor(Color.GRAY).setBackgroundColor(Color.LIGHT_GRAY));
		

		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Código").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Produto").setBold().setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("Quantidade").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER)));

		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("0010").setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("PICOLE LIMAO").setFontSize(12).setTextAlignment(TextAlignment.LEFT)));
		table.addHeaderCell(new Cell().setBorder(Border.NO_BORDER).setBorderBottom(border)
				.add(new Paragraph("50").setFontSize(12).setTextAlignment(TextAlignment.CENTER)));
		
		table.addFooterCell(new Cell(1, 2).add(new Paragraph("Total").setBold().setFontSize(12))
					.setBorder(Border.NO_BORDER)
					.setFontSize(12)
					.setBold()
					.setFontColor(Color.GRAY)
					.setBackgroundColor(Color.LIGHT_GRAY));
		table.addFooterCell(new Cell().add(new Paragraph("50").setBold().setFontSize(12).setTextAlignment(TextAlignment.CENTER))
					.setBorder(Border.NO_BORDER)
					.setFontSize(12)
					.setBold()
					.setFontColor(Color.GRAY)
					.setBackgroundColor(Color.LIGHT_GRAY));
		
		Div divTable = new Div();
		divTable.setMarginTop(30);
		
		Table tableDetail = new Table(new float[] {1, 1});
		tableDetail.setWidthPercent(100)
		.addHeaderCell(new Cell().setBorder(border).add(new Paragraph("Recibo:").setFontSize(12).setBold().add(new Text("17001"))))
		.addHeaderCell(new Cell().setBorder(border).add(new Paragraph("04 de outubro de 2018")
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
		reciboDiv.setBackgroundColor(Color.GRAY)
			.setMinHeight(770);
		
		reciboDiv.add(new Paragraph("RECIBO").setTextAlignment(TextAlignment.CENTER).setFontColor(Color.GRAY)
				.setBackgroundColor(Color.LIGHT_GRAY).setBold().setFontSize(18))
		.add(dados)
		.add(table)
		.add(divTable)
		.add(divAssign);
		
		doc.add(reciboDiv);
		doc.close();
	}
}
