package io.shakhov.calculator.tokenizer.state;

import io.shakhov.calculator.tokenizer.Tokenizer;

public interface State {
    void handle(int c, Tokenizer tokenizer);
}
