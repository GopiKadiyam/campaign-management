package com.gk.campaign.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvProcessorService {

    private final TemplateEngine templateEngine;

    public CsvProcessorService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

//    public List<Map<String, String>> parseCsvFile(MultipartFile file,) throws IOException, CsvValidationException {
//        List<Map<String, String>> records = new ArrayList<>();
//        try (CSVReader csvReader = new CSVReader(new InputStreamReader(csvStream))) {
//            String[] headers = csvReader.readNext();  // First row as headers
//            String[] row;
//
//            while ((row = csvReader.readNext()) != null) {
//                Map<String, String> record = new HashMap<>();
//                for (int i = 0; i < headers.length; i++) {
//                    record.put(headers[i], row[i]);
//                }
//                records.add(record);
//            }
//        }
//        return records;
//    }
    public String renderTemplate(String templateBody, Map<String, String> variables) {
        Context context = new Context();
        variables.forEach(context::setVariable);
        return templateEngine.process(templateBody, context);
    }
}
