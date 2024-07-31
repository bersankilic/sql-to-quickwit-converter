package com.bersan.parser;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;

public class SQLParser {
    
    // bu metot verilen sql sorgusunu parse eder ve bir statement nesnesi döner
    public Statement parseSQL(String sql) throws Exception {
        return CCJSqlParserUtil.parse(sql);
    }
    
}
