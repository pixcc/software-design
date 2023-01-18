package io.shakhov.calculator.tokenizer.state;

import static io.shakhov.calculator.tokenizer.state.Start.START;
import io.shakhov.calculator.token.NumberToken;
import io.shakhov.calculator.tokenizer.Tokenizer;

public class Number implements State {

    private final StringBuilder number = new StringBuilder();

    @Override
    public void handle(int c, Tokenizer tokenizer) {
        if (Character.isDigit(c)) {
            number.append((char) c);
        } else {
            int value = Integer.parseInt(number.toString());
            tokenizer.addToken(new NumberToken(value));
            tokenizer.setState(START);
            START.handle(c, tokenizer);
        }
    }
}
