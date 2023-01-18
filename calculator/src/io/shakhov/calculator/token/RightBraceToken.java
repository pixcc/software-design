package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class RightBraceToken implements Token {
    public static final Token RIGHT_BRACE_TOKEN = new RightBraceToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}