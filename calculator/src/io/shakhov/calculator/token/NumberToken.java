package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public class NumberToken implements Token {

    private final int value;

    public NumberToken(int value) {
        this.value = value;
    }

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }

    public int getValue() {
        return value;
    }
}
