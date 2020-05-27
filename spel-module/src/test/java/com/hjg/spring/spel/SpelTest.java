package com.hjg.spring.spel;

import com.hjg.spring.spel.model.Inventor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.GregorianCalendar;


public class SpelTest {

    @Test
    public void test1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String)exp.getValue();
        Assertions.assertNotNull(message);
        System.out.println("message = " + message);
    }

    @Test
    public void testInvoke() {
        ExpressionParser parser = new SpelExpressionParser();

        //会反射调用getBytes()方法
        Expression exp = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        for(int i=0; i<bytes.length; i++) {
            System.out.print(bytes[i] + " ");
        }
    }

    @Test
    public void testObject() {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1856, 7, 9);

        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor();
        tesla.setName("Nikola Tesla");
        tesla.setBirthdate(c.getTime());
        tesla.setNationality("Serbian");

        ExpressionParser parser = new SpelExpressionParser();

        Expression exp = parser.parseExpression("name");
        String name = exp.getValue(tesla, String.class);
        // name == "Nikola Tesla"
        System.out.println("name = " + name);


        exp = parser.parseExpression("name == 'Nikola Tesla'");
        boolean result = exp.getValue(tesla, Boolean.class);
        // result == true
        System.out.println("result = " + result);
    }
}
