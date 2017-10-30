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
		recibo = dao.load("17001");
		
		PdfUtil pdf = new PdfUtil();
		
		pdf.gerarPdf(Arrays.asList(recibo));
	}

	@Test
	public void codigo_gerar_recibo() throws Exception {
		PdfUtil pdfUtil = new PdfUtil();
		// Passar Lista de Recibos para serem gerados
		pdfUtil.gerarPdf(null);
	}
}
