package io.shakhov.calculator.tokenizer.state;

import io.shakhov.calculator.tokenizer.Tokenizer;

public class Error implements State {
    public static final State ERROR = new Error();

    @Override
    public void handle(int c, Tokenizer tokenizer) {
        throw new RuntimeException("Unexpected symbol = " + (char) c);
    }
}
