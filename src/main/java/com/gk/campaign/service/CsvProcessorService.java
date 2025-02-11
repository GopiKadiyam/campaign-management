package com.gk.campaign.service;

import com.gk.campaign.repository.postgres.TemplateRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CsvProcessorService {

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private MessageSenderServiceImpl messageSenderService;

    private final ExecutorService executor = Executors.newFixedThreadPool(10);  // Thread pool for async tasks

    public void processCsvFile(MultipartFile file, Long campaignId, String templateId) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            String templateBody = templateRepository.findTemplateBodyByTemplateId(templateId);
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                Map<String, String> templateVariables = new HashMap<>();
                for (String header : record.getParser().getHeaderNames()) {
                    String trimmedHeader = header.trim().replaceAll("[^\\x20-\\x7E]", ""); // for clean header
                    if (trimmedHeader.startsWith("var")) {
                        templateVariables.put(trimmedHeader, record.get(header));
                    }
                }
                // Render the template message
                String renderedMessage = renderTemplate(templateBody, templateVariables);
                String phone = templateVariables.get("var1");
                // Send message asynchronously
                CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
                        messageSenderService.sendMessage(phone, renderedMessage, campaignId), executor);
                futures.add(future);
            }

            // Wait for all async tasks to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }
    }

    private String renderTemplate(String templateBody, Map<String, String> variables) {
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            templateBody = templateBody.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return templateBody;
    }

    public static String replaceVariables(String template, Map<String, String> values) {
        // Regular expression to match placeholders like {var1}, {var2}, etc.
        Pattern pattern = Pattern.compile("\\{(var\\d+)\\}");  // \\d+ matches one or more digits

        Matcher matcher = pattern.matcher(template);
        StringBuffer sb = new StringBuffer(); // Use StringBuffer for efficient string building

        while (matcher.find()) {
            String placeholder = matcher.group(1); // Get the captured group (e.g., var3)
            String value = values.get(placeholder);

            if (value != null) {
                matcher.appendReplacement(sb, value); // Replace the placeholder with the value
            } else {
                // Handle the case where the placeholder is not found in the map.
                // You can either keep the placeholder as is, replace with an empty string,
                // or throw an exception.  Here, we keep the placeholder.
                matcher.appendReplacement(sb, matcher.group()); // Append the original placeholder
                System.err.println("Warning: Value not found for placeholder: " + placeholder);
            }
        }
        matcher.appendTail(sb); // Add the remaining part of the string

        return sb.toString();
    }
}
