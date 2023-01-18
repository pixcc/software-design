package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class MulToken implements Token {
    public static final Token MUL_TOKEN = new MulToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}

