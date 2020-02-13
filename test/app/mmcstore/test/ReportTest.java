package app.mmcstore.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;

import app.mmcstore.common.ITextTableGenerator;
import app.mmcstore.common.PdfViewer;
import app.mmcstore.provider.ProviderProductView;

import org.junit.Before;
import org.junit.Test;

public class ReportTest {

    @Test
	public void testReport() {
		File file = new File("C:\\Users\\Learn\\Desktop\\mgg-pics\\pdf\\pdf.pdf");
		if (file != null) {
			String savePath = file.getAbsolutePath();
			// System.out.println(savePath);
			String title = "MMC Store Products Report",
					subTitle = "Provider : MGG" ;
			int colNum = 5;
			List<List<String>> dataSet = new ArrayList<List<String>>();
			String[] tableTitleList = { " Sr. No. ", " Product Name ", " Product Description ",
					" Available Quantity ", " Product Price " };
			dataSet.add(Arrays.asList(tableTitleList));
			for (int j=1;j<=100;j++) {
				List<String> dataLine = new ArrayList<>();
				dataLine.add(" " + j + " ");
				dataLine.add("Product "+j);
				dataLine.add("Description "+j);
				dataLine.add(String.valueOf(j));
				dataLine.add(String.valueOf(j*j));
				dataSet.add(dataLine);
			}
			Document document = new Document(PageSize.A4);
			try {
				ITextTableGenerator.createPdfTable(document, savePath, title, subTitle, colNum, dataSet);
				//PdfViewer.viewPDFFile(savePath, title);
			} catch (IOException ex) {
				Logger.getLogger(ProviderProductView.class.getName()).log(Level.ERROR, null, ex);
			} catch (DocumentException ex) {
				Logger.getLogger(ProviderProductView.class.getName()).log(Level.ERROR, null, ex);
			}
		}
	}

}
