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

import javax.swing.plaf.nimbus.State;

public class QuickwitQueryConverter {
    
    // Bu metot, verilen SQL Statement nesnesini Quickwit sorgusuna çevirir
    public String convert(Statement sqlStatement) {
        if (sqlStatement instanceof Select) {
            // Eğer SQL sorgusu bir Select sorgusu ise, bu sorguyu Quickwit sorgusuna çevirir
            return convertSelectQuery((Select) sqlStatement);
        }
        // Diğer SQL sorgu türleri için dönüşüm mantığı buraya eklenebilir
        return null;
    }
    
    
    
    // Bu metot, Select sorgusunu Quickwit sorgusuna çevirme mantığını içerir
    private String convertSelectQuery(Select select) {
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        
        // Tablo ismini al
        String tableName = plainSelect.getFromItem().toString();
        System.out.println("tablo ismi : " + tableName);
        
        // Select edilen kolonları al
        StringBuilder fields = new StringBuilder();
        for(SelectItem item : plainSelect.getSelectItems()){
            if(fields.length() > 0){
                fields.append(", ");
            }
            fields.append(item.toString());
        }
        System.out.println("sütun : " + fields.toString());
        System.out.println("-----------------\n");
        
        // WHERE koşulunu al
        String whereClause = "";
        if (plainSelect.getWhere() != null) {
            whereClause = convertExpressionToQuickwit(plainSelect.getWhere());
        }
        
        
        
        // Quickwit sorgusunu oluştur
        //String quickwitQuery = String.format("(%s) %s", fields.toString(), whereClause);
        String quickwitQuery = String.format(whereClause);
        return quickwitQuery;
    }
    
    // Bu metot, SQL WHERE ifadesini Quickwit sorgusuna çevirir
    private String convertExpressionToQuickwit(Expression expression) {
        if (expression instanceof AndExpression) {
            AndExpression andExpression = (AndExpression) expression;
            return String.format("(%s AND %s)",
                    convertExpressionToQuickwit(andExpression.getLeftExpression()),
                    convertExpressionToQuickwit(andExpression.getRightExpression()));
        } else if (expression instanceof OrExpression) {
            OrExpression orExpression = (OrExpression) expression;
            return String.format("(%s OR %s)",
                    convertExpressionToQuickwit(orExpression.getLeftExpression()),
                    convertExpressionToQuickwit(orExpression.getRightExpression()));
        } else if (expression instanceof EqualsTo) {
            EqualsTo equalsTo = (EqualsTo) expression;
            return String.format("%s:%s",
                    equalsTo.getLeftExpression().toString(),
                    equalsTo.getRightExpression().toString());
        } else if (expression instanceof GreaterThan) {
            GreaterThan greaterThan = (GreaterThan) expression;
            return String.format("%s:>%s",
                    greaterThan.getLeftExpression().toString(),
                    greaterThan.getRightExpression().toString());
        } else if (expression instanceof MinorThan) {
            MinorThan minorThan = (MinorThan) expression;
            return String.format("%s:<%s",
                    minorThan.getLeftExpression().toString(),
                    minorThan.getRightExpression().toString());
        } else {
            return expression.toString();
        }
        
        
       
        
    }
}