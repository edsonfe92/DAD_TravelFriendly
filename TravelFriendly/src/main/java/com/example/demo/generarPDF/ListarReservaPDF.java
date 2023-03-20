package com.example.demo.generarPDF;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
@Component("/accionReserva")
public class ListarReservaPDF extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PdfPTable tablaReserva= new PdfPTable(1);
		String error = (String) model.get("error");
		String o = (String) model.get("o");
		String d = (String) model.get("d");
		String f = (String) model.get("f");
		tablaReserva.addCell(error);
		tablaReserva.addCell(o);
		tablaReserva.addCell(d);
		tablaReserva.addCell(f);
		document.add(tablaReserva);
	}

}
