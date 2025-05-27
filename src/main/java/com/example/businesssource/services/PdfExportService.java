package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import org.springframework.beans.factory.annotation.Autowired;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.io.OutputStream;





import java.io.OutputStream;


    @Service
    public class PdfExportService {

        @Autowired
        private SpringTemplateEngine templateEngine;

        public void generatePdf(BusinessPlan plan, OutputStream outputStream) throws IOException {
            Context context = new Context();
            context.setVariable("plan", plan);

            String html = templateEngine.process("pdf/business-plan-pdf", context);

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(outputStream);
            builder.run();
        }
    }


