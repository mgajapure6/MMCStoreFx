package app.mmcstore.common;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {

	public static void getPdf(String filePath,String title, String[] colArr, String[] dataArr) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			Paragraph titleP = new Paragraph(title);
			//titleP.setFont(new Font(FontFamily.TIMES_ROMAN, 30, Font.BOLD));
			document.add(titleP);
			
			//document.add( Chunk.NEWLINE );
	        //document.add( Chunk.NEWLINE );
	        
	        
			PdfPTable table = new PdfPTable(colArr.length); // 3 columns.
	        table.setWidthPercentage(100); //Width 100%
	        table.setSpacingBefore(20f); //Space before table
	        table.setSpacingAfter(20f); //Space after table
	        table.setSpacingBefore(10); 
	        table.setSpacingAfter(10); 
			
	        
	        //Set Column widths
	        float[] columnWidths = new float[colArr.length];
	        for (int i = 0; i < colArr.length; i++) {
	        	columnWidths[i]= 1f;
			}
	        table.setWidths(columnWidths);
	        
	        for (int i = 0; i < colArr.length; i++) {
	        	Paragraph p = new Paragraph(title);
	        	//p.setFont(new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLD,new BaseColor(117, 117, 117)));
	        	PdfPCell cell = new PdfPCell(p);
	        	cell.setBorderWidth(1);
	            cell.setBorderColor(new BaseColor(236, 236, 236));
	            cell.setPadding(10);
	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
	        
	        for (int i = 0; i < colArr.length; i++) {
	        	Paragraph p = new Paragraph(title);
	        	p.setFont(new Font(FontFamily.TIMES_ROMAN, 13, Font.NORMAL,new BaseColor(117, 117, 117)));
	        	PdfPCell cell = new PdfPCell(p);
	        	cell.setBorderWidth(1);
	            cell.setBorderColor(new BaseColor(236, 236, 236));
	            cell.setPadding(10);
	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
			}
	        
	        
	        
	        document.add(table);
			
			document.close();
			writer.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
