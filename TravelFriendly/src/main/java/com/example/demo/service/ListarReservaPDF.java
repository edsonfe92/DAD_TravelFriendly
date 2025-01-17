package com.example.demo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ListarReservaPDF {
	public void export(HttpServletResponse response, String o, String d, String f, String user) throws DocumentException, IOException {
		// TODO Auto-generated method stub
		//declaramos los objetos necesarios para sacar información de nuestra reservas y recogemos su información
		//mediante Strings
		
		//Formato ddel pdf:
		 
	    //Fin formato PDF
		Document document = new Document(PageSize.A4);
		
	
		//creamos una vista PDF
		
		PdfWriter.getInstance(document, response.getOutputStream()); 
		//abrimos vista pdf
		document.open();
		//añadimos el texto que nos interesa:
		com.lowagie.text.Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(20);
		
		Paragraph p1= new Paragraph("Enhorabuena, tu reserva ha sido completada", fontTitle);
		p1.setAlignment(Paragraph.ALIGN_CENTER);
		
		com.lowagie.text.Font fontParagraph= FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(14);
		
		Paragraph p2= new Paragraph("Hola "+user+" tu reserva es: "+o+"-"+d+": "+f+"", fontParagraph);
		p2.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p1);
		document.add(p2);
		document.close();
	}
	public void export(  String o, String d, String f, String user) throws DocumentException, IOException {
		
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		
		PdfWriter.getInstance(document, baos); 
		//abrimos vista pdf
		document.open();
		//añadimos el texto que nos interesa:
		

		com.lowagie.text.Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(20);
		
		Paragraph p1= new Paragraph("Enhorabuena, tu reserva ha sido completada", fontTitle);
		p1.setAlignment(Paragraph.ALIGN_CENTER);
		
		com.lowagie.text.Font fontParagraph= FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(14);
		
		Paragraph p2= new Paragraph("Hola "+user+" tu reserva es: "+o+"-"+d+": "+f+"", fontParagraph);
		p2.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p1);
		document.add(p2);
		//document.close();
	}

}
