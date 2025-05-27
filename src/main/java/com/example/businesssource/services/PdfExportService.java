package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

@Service
public class PdfExportService {

    public void generatePdf(BusinessPlan businessPlan, OutputStream outputStream) {
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Business Plan"));
            document.add(new Paragraph("Company Name: " + businessPlan.getCompanyName()));
            document.add(new Paragraph("Company Description: " + businessPlan.getCompanyDescription()));
            document.add(new Paragraph("Market Analysis: " + businessPlan.getMarketAnalysis()));
            document.add(new Paragraph("Organization and Management: " + businessPlan.getOrganizationManagement()));
            document.add(new Paragraph("Products and Services: " + businessPlan.getProductsServices()));
            document.add(new Paragraph("Marketing Strategy: " + businessPlan.getMarketingStrategy()));
            document.add(new Paragraph("Financial Projections: " + businessPlan.getFinancialProjections()));
            document.add(new Paragraph("Funding Request: " + businessPlan.getFundingRequest()));

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
