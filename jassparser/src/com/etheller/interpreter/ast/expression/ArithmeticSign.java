package com.etheller.interpreter.ast.expression;

import com.etheller.interpreter.ast.value.*;

public interface ArithmeticSign {
    JassValue apply(BooleanJassValue left, BooleanJassValue right);

    JassValue apply(RealJassValue left, RealJassValue right);

    JassValue apply(RealJassValue left, IntegerJassValue right);

    JassValue apply(IntegerJassValue left, RealJassValue right);

    JassValue apply(IntegerJassValue left, IntegerJassValue right);

    JassValue apply(String left, String right);

    JassValue apply(HandleJassValue left, HandleJassValue right);

    JassValue apply(CodeJassValue left, CodeJassValue right);
}
