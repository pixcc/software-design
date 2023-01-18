package io.shakhov.calculator.tokenizer;

import java.util.ArrayList;
import java.util.List;
import static io.shakhov.calculator.tokenizer.state.Start.START;
import io.shakhov.calculator.token.Token;
import io.shakhov.calculator.tokenizer.state.State;

public class Tokenizer {
    public static final int EOF = -1;

    private State state;
    private List<Token> tokens;

    public List<Token> tokenize(String input) {
        state = START;
        tokens = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            state.handle(input.charAt(i), this);
        }
        state.handle(EOF, this);
        return tokens;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }
}
