package es.codeurjc.web.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.lowagie.text.DocumentException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.context.FacesContext;

@Controller
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
