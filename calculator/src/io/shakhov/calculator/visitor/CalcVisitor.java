package io.shakhov.calculator.visitor;

import java.util.ArrayList;
import java.util.List;
import io.shakhov.calculator.token.AddToken;
import io.shakhov.calculator.token.DivToken;
import io.shakhov.calculator.token.LeftBraceToken;
import io.shakhov.calculator.token.MulToken;
import io.shakhov.calculator.token.NumberToken;
import io.shakhov.calculator.token.RightBraceToken;
import io.shakhov.calculator.token.SubToken;
import io.shakhov.calculator.token.Token;

public class CalcVisitor implements TokenVisitor {
    private List<Integer> stack;

    @Override
    public void visit(NumberToken token) {
        stack.add(token.getValue());
    }

    @Override
    public void visit(MulToken token) {
        stack.add(stack.remove(stack.size() - 1) * stack.remove(stack.size() - 1));
    }

    @Override
    public void visit(DivToken token) {
        int b = stack.remove(stack.size() - 1);
        int a = stack.remove(stack.size() - 1);
        stack.add(a / b);
    }

    @Override
    public void visit(AddToken token) {
        stack.add(stack.remove(stack.size() - 1) + stack.remove(stack.size() - 1));
    }

    @Override
    public void visit(SubToken token) {
        int b = stack.remove(stack.size() - 1);
        int a = stack.remove(stack.size() - 1);
        stack.add(a - b);
    }

    @Override
    public void visit(LeftBraceToken leftBraceToken) {
        throw new IllegalStateException("Left brace token is not expected");
    }

    @Override
    public void visit(RightBraceToken rightBraceToken) {
        throw new IllegalStateException("Right brace token is not expected");
    }

    public int calculate(List<Token> expression) {
        stack = new ArrayList<>();
        for (Token token : expression) {
            token.accept(this);
        }
        return stack.remove(stack.size() - 1);
    }
}
