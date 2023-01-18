package io.shakhov.calculator.tokenizer.state;

import static io.shakhov.calculator.tokenizer.state.Error.ERROR;
import io.shakhov.calculator.tokenizer.Tokenizer;

public class End implements State {
    public static final State END = new End();

    @Override
    public void handle(int c, Tokenizer tokenizer) {
        tokenizer.setState(ERROR);
        ERROR.handle(c, tokenizer);
    }
}
