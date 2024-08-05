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
            // quickwit sorgusunu HTTP endpointine yolla ve cevabı al
            String response = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:7280/api/v1/stackoverflow/search?query=" + quickwitQuery)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            // alınan cevabı konsola yaz
            System.out.println(response);
            return quickwitQuery;
        } catch (Exception e) {
            e.printStackTrace();
            return "Hata: " + e.getMessage();
        }
    }
}

