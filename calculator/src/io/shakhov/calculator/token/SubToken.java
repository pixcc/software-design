package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class SubToken implements Token {
    public static final Token SUB_TOKEN = new SubToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
