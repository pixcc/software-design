package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class DivToken implements Token {
    public static final Token DIV_TOKEN = new DivToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
