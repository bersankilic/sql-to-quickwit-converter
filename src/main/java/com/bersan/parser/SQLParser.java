package com.bersan.parser;

import net.sf.jsqlparser.parser.CCJSqlParserTreeConstants;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.web.client.RestTemplate;

public class SQLParser {
    
    // Bu metot, verilen SQL sorgusunu parse eder ve bir Statement nesnesi döner
    public Statement parseSQL(String sql) throws Exception {
        return CCJSqlParserUtil.parse(sql);
    }
    
    RestTemplate restTemplate = new RestTemplate();
    
}
