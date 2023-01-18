package io.shakhov.calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import io.shakhov.calculator.token.Token;
import io.shakhov.calculator.tokenizer.Tokenizer;
import io.shakhov.calculator.visitor.CalcVisitor;
import io.shakhov.calculator.visitor.ParserVisitor;
import io.shakhov.calculator.visitor.PrintVisitor;

public class Calculator {

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String input = bufferedReader.readLine();
            List<Token> expression = new Tokenizer().tokenize(input);
            List<Token> rpnExpression = new ParserVisitor().parse(expression);
            System.out.print("RPN Expression: ");
            new PrintVisitor(System.out).print(rpnExpression);
            int result = new CalcVisitor().calculate(rpnExpression);
            System.out.println("Evaluated result: " + result);
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }
}
