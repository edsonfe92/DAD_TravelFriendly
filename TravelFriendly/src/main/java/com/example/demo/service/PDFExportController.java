package com.example.demo.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;

import com.lowagie.text.DocumentException;


@Configuration
public class PDFExportController {

	
	private final ListarReservaPDF pdfGeneratorService;

    public PDFExportController(ListarReservaPDF pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }
   
    public void generatePDF( HttpServletResponse response, String o, String d, String f, String user) throws IOException {

        
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";

        response.setHeader(headerKey, headerValue);

        this.pdfGeneratorService.export(response,o,d,f,user);
    }
    
    public void generatePDF(String o,String d, String f,String user) throws DocumentException, IOException {
    	 this.pdfGeneratorService.export(o,d,f,user);
    }
}
