package com.bersan.parser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
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

    
}
