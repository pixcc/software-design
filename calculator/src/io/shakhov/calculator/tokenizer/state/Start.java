package io.shakhov.calculator.tokenizer.state;

import static io.shakhov.calculator.token.AddToken.ADD_TOKEN;
import static io.shakhov.calculator.token.DivToken.DIV_TOKEN;
import static io.shakhov.calculator.token.LeftBraceToken.LEFT_BRACE_TOKEN;
import static io.shakhov.calculator.token.MulToken.MUL_TOKEN;
import static io.shakhov.calculator.token.RightBraceToken.RIGHT_BRACE_TOKEN;
import static io.shakhov.calculator.token.SubToken.SUB_TOKEN;
import static io.shakhov.calculator.tokenizer.Tokenizer.EOF;
import static io.shakhov.calculator.tokenizer.state.End.END;
import static io.shakhov.calculator.tokenizer.state.Error.ERROR;
import io.shakhov.calculator.tokenizer.Tokenizer;

public class Start implements State {
    public static final State START = new Start();

    @Override
    public void handle(int c, Tokenizer tokenizer) {
        if (Character.isDigit(c)) {
            Number newState = new Number();
            tokenizer.setState(newState);
            newState.handle(c, tokenizer);
        } else if (c == ')') {
            tokenizer.addToken(RIGHT_BRACE_TOKEN);
        } else if (c == '(') {
            tokenizer.addToken(LEFT_BRACE_TOKEN);
        } else if (c == '*') {
            tokenizer.addToken(MUL_TOKEN);
        } else if (c == '-') {
            tokenizer.addToken(SUB_TOKEN);
        } else if (c == '+') {
            tokenizer.addToken(ADD_TOKEN);
        } else if (c == '/') {
            tokenizer.addToken(DIV_TOKEN);
        } else if (Character.isWhitespace(c)) {
            // skip
        } else if (c == EOF) {
            tokenizer.setState(END);
        } else {
            tokenizer.setState(ERROR);
            ERROR.handle(c, tokenizer);
        }
    }
}
