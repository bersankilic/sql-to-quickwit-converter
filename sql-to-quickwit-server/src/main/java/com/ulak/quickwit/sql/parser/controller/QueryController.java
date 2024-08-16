package com.ulak.quickwit.sql.parser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ulak.quickwit.sql.parser.QuickwitQueryConverter;
import com.ulak.quickwit.sql.parser.SQLParser;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class QueryController { // HTTP isteklerini dinleyen ve işleyen sınıf

    private final SQLParser sqlParser = new SQLParser();
    private final QuickwitQueryConverter queryConverter = new QuickwitQueryConverter();

    @Autowired
    private WebClient.Builder webClientBuilder; // HTTP istekleri yapmak için kullanılacak WebClient nesnesi


    // bu endpoint post isteği ile gelen sql sorgusunu alacak ve quickwit sorgusuna dönüştürecek
    @PostMapping("/convert-sql")
    public String convertSQLToQuickwit(@RequestBody String sqlQuery) {
        try {
            // gelen sql sorgusunu parse eder
            Statement statement = sqlParser.parseSQL(sqlQuery);
            // parse edilen sql sorgusunu Quickwit sorgusuna dönüştürür
            String quickwitQuery = queryConverter.convert(statement);
            // quickwit sorgusunu HTTP endpointine gönder ve cevabı al
            String response = webClientBuilder.build()
                    .post()
                    .uri("http://localhost:7280/api/v1/stackoverflow/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(quickwitQuery)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            // alınan cevabı json formatına getirip konsola yaz
            ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
            String responseJson = ow.writeValueAsString(new ObjectMapper().readTree(response));
            System.out.println(responseJson);
            return quickwitQuery;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

