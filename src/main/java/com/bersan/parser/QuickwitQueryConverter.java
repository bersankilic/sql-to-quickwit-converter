package com.bersan.parser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectItem;


public class QuickwitQueryConverter {
    
    // sql statementi quickwit sorgusuna çevir
    public String convert(Statement sqlStatement) {
        if (sqlStatement instanceof Select) {
            // eğer sql sorgusu bir select sorgusu ise convertSelectQuery metodunu çağır
            return convertSelectQuery((Select) sqlStatement);
        }
        // diğer sql sorgu türleri için dönüşüm kodları buraya eklenecek
        return null;
    }
    
    
    
    // select sorgusunu quickwit sorgusuna çeviren metot
    private String convertSelectQuery(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        
        // tablo ismini al
        String tableName = plainSelect.getFromItem().toString();
        System.out.println("tablo ismi : " + tableName); //kontrol amaçlı ekledim
        
        // kolon isimlerini al
        StringBuilder fields = new StringBuilder();
        for(SelectItem item : plainSelect.getSelectItems()){
            if(fields.length() > 0){
                fields.append(", ");
            }
            fields.append(item.toString());
        }
        System.out.println("sütun : " + fields.toString()); //kontrol amaçlı ekledim
        System.out.println("-----------------\n");
        
        // where koşulunu al
        String whereClause = ""; // where koşulu yoksa boş string
        if (plainSelect.getWhere() != null) {
            whereClause = convertExpressionToQuickwit(plainSelect.getWhere()); // where koşulunu quickwit sorgusuna çevir
        }
        // quickwit sorgusunu oluştur
        // String quickwitQuery = String.format("(%s) %s", fields.toString(), whereClause);
        String quickwitQuery = String.format(whereClause);
        return quickwitQuery;
    }
    
    // where ifadesini quickwit sorgusuna çeviren metot
    private String convertExpressionToQuickwit(Expression expression) { // expression ifadesini alır
        if (expression instanceof AndExpression) { // and ifadesi ise
            AndExpression andExpression = (AndExpression) expression;
            return String.format("(%s AND %s)",
                    convertExpressionToQuickwit(andExpression.getLeftExpression()),
                    convertExpressionToQuickwit(andExpression.getRightExpression()));
        } else if (expression instanceof OrExpression) { //or ifadesi ise
            OrExpression orExpression = (OrExpression) expression;
            return String.format("(%s OR %s)",
                    convertExpressionToQuickwit(orExpression.getLeftExpression()),
                    convertExpressionToQuickwit(orExpression.getRightExpression()));
        } else if (expression instanceof EqualsTo) { // eşittir ifadesi ise
            EqualsTo equalsTo = (EqualsTo) expression;
            return String.format("%s:%s",
                    equalsTo.getLeftExpression().toString(),
                    equalsTo.getRightExpression().toString());
        } else if (expression instanceof GreaterThan) { // büyüktür ifadesi ise
            GreaterThan greaterThan = (GreaterThan) expression;
            return String.format("%s:>%s",
                    greaterThan.getLeftExpression().toString(),
                    greaterThan.getRightExpression().toString());
        } else if (expression instanceof MinorThan) { // küçüktür ifadesi ise
            MinorThan minorThan = (MinorThan) expression;
            return String.format("%s:<%s",
                    minorThan.getLeftExpression().toString(),
                    minorThan.getRightExpression().toString());
        } else {
            return expression.toString(); // diğer durumlar için expression'ı stringe çevirip döndür
        }
        
        
       
        
    }
}