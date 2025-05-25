package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.repositories.BusinessPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import java.io.OutputStream;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessPlanService {

    @Autowired
    private BusinessPlanRepository businessPlanRepository;

    public BusinessPlan save(BusinessPlan businessPlan) {
        System.out.println("Saving Business Plan: " + businessPlan.getCompanyName());
        return businessPlanRepository.save(businessPlan);
    }


    public Optional<BusinessPlan> findById(Long id) {
        return businessPlanRepository.findById(id);
    }

    public List<BusinessPlan> findAll() {
        return businessPlanRepository.findAll();
    }

    public void deleteBusinessPlan(Long id) {
        BusinessPlan plan = businessPlanRepository.findById(id).orElseThrow();
        businessPlanRepository.delete(plan);
    }



    public BusinessPlan findBusinessPlanById(Long id) {
        return businessPlanRepository.findById(id).orElse(null);
    }
    public void generatePdf(BusinessPlan businessPlan, OutputStream outputStream) {
        PdfWriter writer = new PdfWriter(outputStream);
        Document document = new Document(new com.itextpdf.kernel.pdf.PdfDocument(writer));

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
    }
}




