package com.bersan.parser.controller;

import com.bersan.parser.QuickwitQueryConverter;
import com.bersan.parser.SQLParser;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    
    private final SQLParser sqlParser = new SQLParser();
    private final QuickwitQueryConverter queryConverter = new QuickwitQueryConverter();
    
    // Bu endpoint, POST isteği ile gelen SQL sorgusunu alır ve Quickwit sorgusuna dönüştürür
    @PostMapping("/convert-sql")
    public String convertSQLToQuickwit(@RequestBody String sqlQuery) {
        try {
            // Gelen SQL sorgusunu parse eder
            Statement statement = sqlParser.parseSQL(sqlQuery);
            // Parse edilen SQL sorgusunu Quickwit sorgusuna dönüştürür
            return queryConverter.convert(statement);
        } catch (Exception e) {
            e.printStackTrace();
            // Hata durumunda geri dönen mesaj
            return "Error parsing or converting SQL query.";
        }
    }
}

