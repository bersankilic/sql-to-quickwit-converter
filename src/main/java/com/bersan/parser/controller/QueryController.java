package com.bersan.parser.controller;

import com.bersan.parser.QuickwitQueryConverter;
import com.bersan.parser.SQLParser;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class QueryController {
    
    private final SQLParser sqlParser = new SQLParser();
    private final QuickwitQueryConverter queryConverter = new QuickwitQueryConverter();
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    
    // Bu endpoint, POST isteği ile gelen SQL sorgusunu alır ve Quickwit sorgusuna dönüştürür
    @PostMapping("/convert-sql")
    public String convertSQLToQuickwit(@RequestBody String sqlQuery) {
        try {
            // Gelen SQL sorgusunu parse eder
            Statement statement = sqlParser.parseSQL(sqlQuery);
            // Parse edilen SQL sorgusunu Quickwit sorgusuna dönüştürür
            String quickwitQuery = queryConverter.convert(statement);
            //Quickwit sorgusunu HTTP endpointine yolla ve cevabı al
            String response = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:7280/api/v1/quickwitte/search?query=" + quickwitQuery)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            //Alınan cevabı konsola yaz
            System.out.println(response);
            return quickwitQuery;
        } catch (Exception e) {
            e.printStackTrace();
            // Hata durumunda geri dönen mesaj
            return "Error parsing or converting SQL query.";
        }
    }
}

