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

public class ParserVisitor implements TokenVisitor {
    private List<Token> stack;
    private List<Token> result;

    @Override
    public void visit(NumberToken numberToken) {
        result.add(numberToken);
    }

    @Override
    public void visit(LeftBraceToken leftBraceToken) {
        stack.add(leftBraceToken);
    }

    @Override
    public void visit(RightBraceToken rightBraceToken) {
        boolean foundMatchingLeftBrace = false;
        while (!stack.isEmpty()) {
            Token token = stack.remove(stack.size() - 1);
            if (token instanceof LeftBraceToken) {
                foundMatchingLeftBrace = true;
                break;
            }
            result.add(token);
        }

        if (!foundMatchingLeftBrace) {
            throw new RuntimeException("Didn't found matching left brace");
        }
    }

    @Override
    public void visit(AddToken addToken) {
        visitLowPriorityOp(addToken);
    }

    @Override
    public void visit(SubToken subToken) {
        visitLowPriorityOp(subToken);
    }

    private void visitLowPriorityOp(Token opToken) {
        while (!stack.isEmpty()) {
            Token token = stack.remove(stack.size() - 1);
            if (token instanceof LeftBraceToken) {
                stack.add(token);
                break;
            }
            result.add(token);
        }
        stack.add(opToken);
    }

    @Override
    public void visit(MulToken mulToken) {
        visitHighPriorityOp(mulToken);
    }

    @Override
    public void visit(DivToken divToken) {
        visitHighPriorityOp(divToken);
    }

    private void visitHighPriorityOp(Token opToken) {
        while (!stack.isEmpty()) {
            Token token = stack.remove(stack.size() - 1);
            if (token instanceof AddToken
                    || token instanceof SubToken
                    || token instanceof LeftBraceToken) {
                stack.add(token);
                break;
            }
            result.add(token);
        }
        stack.add(opToken);
    }

    public List<Token> parse(List<Token> expression) {
        stack = new ArrayList<>();
        result = new ArrayList<>();
        for (Token token : expression) {
            token.accept(this);
        }
        while (!stack.isEmpty()) {
            Token token = stack.remove(stack.size() - 1);
            if (token instanceof LeftBraceToken) {
                throw new RuntimeException("Didn't found matching right brace");
            }
            result.add(token);
        }
        return result;
    }
}
