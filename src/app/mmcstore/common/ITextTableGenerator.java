package app.mmcstore.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author Mgg
 */
public class ITextTableGenerator {

	public static void createPdfTable(Document document, String savePath, String title, String subTitle, int colNum,
			List<List<String>> dataSet) throws IOException, DocumentException {
		document.setMargins(30, 30, 70, 30);
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(savePath));
		PdfHeader event = new PdfHeader(title, subTitle);
		writer.setPageEvent(event);
		document.open();

		PdfPTable table = new PdfPTable(colNum);
		table.setWidthPercentage(100);

		for (List<String> record : dataSet) {
			for (String field : record) {
				PdfPCell cell = new PdfPCell();
				Paragraph p = new Paragraph(field, FontFactory.getFont(FontFactory.TIMES, 8, BaseColor.BLACK));

				if (isInteger(field.trim()) || isDouble(field.trim())) {
					p.setAlignment(Element.ALIGN_RIGHT);
				} else {
					p.setAlignment(Element.ALIGN_LEFT);
				}
				cell.setVerticalAlignment(Element.ALIGN_CENTER);
				cell.setBorderColor(BaseColor.GRAY);
				cell.addElement(p);
				cell.setPadding(5);
				table.addCell(cell);
			}
		}
		document.add(table);
		document.close();
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	public static class PdfHeader extends PdfPageEventHelper {
		String title;
		String subtitle;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSubtitle() {
			return subtitle;
		}

		public void setSubtitle(String subtitle) {
			this.subtitle = subtitle;
		}

		public PdfHeader(String title, String subtitle) {
			super();
			this.title = title;
			this.subtitle = subtitle;
		}

		@Override
		public void onEndPage(PdfWriter writer, Document document) {
			try {
				Rectangle pageSize = document.getPageSize();
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase(getTitle(), FontFactory.getFont(FontFactory.TIMES, 13, BaseColor.BLACK)),
						pageSize.getWidth()/2, pageSize.getTop(30), 0);
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase(getSubtitle(), FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK)),
						pageSize.getWidth()/2, pageSize.getTop(45), 0);
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
						new Phrase("Report Date : " + DateFormatUtil.dateToString(new Date(), "dd-MM-yyyy"),
								FontFactory.getFont(FontFactory.TIMES, 10, BaseColor.DARK_GRAY)),
						pageSize.getWidth()/2, pageSize.getTop(60), 0);
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,
						new Phrase(String.format("page no :%s", String.valueOf(writer.getCurrentPageNumber())),
								FontFactory.getFont(FontFactory.TIMES, 5, BaseColor.GRAY)),
						pageSize.getRight(30), pageSize.getTop(30), 0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	
	}
}