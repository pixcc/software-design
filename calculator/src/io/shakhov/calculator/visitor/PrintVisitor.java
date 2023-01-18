package io.shakhov.calculator.visitor;

import java.io.PrintStream;
import java.util.List;
import io.shakhov.calculator.token.AddToken;
import io.shakhov.calculator.token.DivToken;
import io.shakhov.calculator.token.LeftBraceToken;
import io.shakhov.calculator.token.MulToken;
import io.shakhov.calculator.token.NumberToken;
import io.shakhov.calculator.token.RightBraceToken;
import io.shakhov.calculator.token.SubToken;
import io.shakhov.calculator.token.Token;

public class PrintVisitor implements TokenVisitor {
    private final PrintStream out;

    public PrintVisitor(PrintStream out) {
        this.out = out;
    }

    @Override
    public void visit(AddToken token) {
        out.print("+");
    }

    @Override
    public void visit(SubToken token) {
        out.print("-");
    }

    @Override
    public void visit(LeftBraceToken leftBraceToken) {
        out.print("(");
    }

    @Override
    public void visit(RightBraceToken rightBraceToken) {
        out.print(")");
    }

    @Override
    public void visit(MulToken token) {
        out.print("*");
    }

    @Override
    public void visit(DivToken token) {
        out.print("/");
    }

    @Override
    public void visit(NumberToken token) {
        out.print(token.getValue());
    }

    public void print(List<Token> expression) {
        for (Token token : expression) {
            token.accept(this);
            out.print(" ");
        }
        out.println();
    }
}

