package io.shakhov.calculator.visitor;

import io.shakhov.calculator.token.AddToken;
import io.shakhov.calculator.token.DivToken;
import io.shakhov.calculator.token.LeftBraceToken;
import io.shakhov.calculator.token.MulToken;
import io.shakhov.calculator.token.NumberToken;
import io.shakhov.calculator.token.RightBraceToken;
import io.shakhov.calculator.token.SubToken;

public interface TokenVisitor {
    void visit(AddToken addToken);

    void visit(MulToken mulToken);

    void visit(DivToken divToken);

    void visit(SubToken subToken);

    void visit(LeftBraceToken leftBraceToken);

    void visit(RightBraceToken rightBraceToken);

    void visit(NumberToken numberToken);
}
