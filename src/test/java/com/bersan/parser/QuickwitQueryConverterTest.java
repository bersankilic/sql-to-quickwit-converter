package com.bersan.parser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuickwitQueryConverterTest {
    
    @Test
    void convertExpressionToQuickwit_shouldConvertBetweenExpression() {
        Between between = mock(Between.class);
        Expression leftExpression = mock(Expression.class);
        Expression startExpression = mock(Expression.class);
        Expression endExpression = mock(Expression.class);

        when(between.getLeftExpression()).thenReturn(leftExpression);
        when(between.getBetweenExpressionStart()).thenReturn(startExpression);
        when(between.getBetweenExpressionEnd()).thenReturn(endExpression);
        when(leftExpression.toString()).thenReturn("field");
        when(startExpression.toString()).thenReturn("10");
        when(endExpression.toString()).thenReturn("20");

        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(between);

        assertEquals("field:[10 TO 20]", result);
    }
    
    @Test
    void convertExpressionToQuickwit_shouldConvertEqualsToExpression() {
        EqualsTo equalsTo = mock(EqualsTo.class);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        
        when(equalsTo.getLeftExpression()).thenReturn(leftExpression);
        when(equalsTo.getRightExpression()).thenReturn(rightExpression);
        when(leftExpression.toString()).thenReturn("field");
        when(rightExpression.toString()).thenReturn("value");
        
        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(equalsTo);
        
        assertEquals("field:value", result);
    }
    
    @Test
    void convertExpressionToQuickwit_shouldConvertGreaterThanExpression() {
        GreaterThan greaterThan = mock(GreaterThan.class);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        
        when(greaterThan.getLeftExpression()).thenReturn(leftExpression);
        when(greaterThan.getRightExpression()).thenReturn(rightExpression);
        when(leftExpression.toString()).thenReturn("field");
        when(rightExpression.toString()).thenReturn("10");
        
        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(greaterThan);
        
        assertEquals("field:>10", result);
    }
    
    @Test
    void convertExpressionToQuickwit_shouldConvertMinorThanExpression() {
        MinorThan minorThan = mock(MinorThan.class);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        
        when(minorThan.getLeftExpression()).thenReturn(leftExpression);
        when(minorThan.getRightExpression()).thenReturn(rightExpression);
        when(leftExpression.toString()).thenReturn("field");
        when(rightExpression.toString()).thenReturn("10");
        
        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(minorThan);
        
        assertEquals("field:<10", result);
    }
    
    
    @Test
    void convertExpressionToQuickwit_shouldConvertAndExpression() {
        AndExpression andExpression = mock(AndExpression.class);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        
        when(andExpression.getLeftExpression()).thenReturn(leftExpression);
        when(andExpression.getRightExpression()).thenReturn(rightExpression);
        when(leftExpression.toString()).thenReturn("field1:value1");
        when(rightExpression.toString()).thenReturn("field2:value2");
        
        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(andExpression);
        
        assertEquals("(field1:value1 AND field2:value2)", result);
    }
    
    @Test
    void convertExpressionToQuickwit_shouldConvertOrExpression() {
        OrExpression orExpression = mock(OrExpression.class);
        Expression leftExpression = mock(Expression.class);
        Expression rightExpression = mock(Expression.class);
        
        when(orExpression.getLeftExpression()).thenReturn(leftExpression);
        when(orExpression.getRightExpression()).thenReturn(rightExpression);
        when(leftExpression.toString()).thenReturn("field1:value1");
        when(rightExpression.toString()).thenReturn("field2:value2");
        
        QuickwitQueryConverter converter = new QuickwitQueryConverter();
        String result = converter.convertExpressionToQuickwit(orExpression);
        
        assertEquals("(field1:value1 OR field2:value2)", result);
    }
    
    
}
