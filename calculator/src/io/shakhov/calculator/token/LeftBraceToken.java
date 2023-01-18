package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class LeftBraceToken implements Token {
    public static final Token LEFT_BRACE_TOKEN = new LeftBraceToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
