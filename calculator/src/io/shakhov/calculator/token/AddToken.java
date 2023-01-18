package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class AddToken implements Token {
    public static final Token ADD_TOKEN = new AddToken();

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}
