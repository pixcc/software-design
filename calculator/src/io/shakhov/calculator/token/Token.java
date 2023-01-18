package io.shakhov.calculator.token;

import io.shakhov.calculator.visitor.TokenVisitor;

public interface Token {
    void accept(TokenVisitor visitor);
}
