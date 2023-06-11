package com.etheller.interpreter.ast.value;

import com.etheller.interpreter.ast.statement.JassReturnNothingStatement;

public interface JassType {
    PrimitiveJassType INTEGER = new PrimitiveJassType("integer", IntegerJassValue.ZERO);
    PrimitiveJassType STRING = new StringJassType("string");
    PrimitiveJassType CODE = new CodeJassType("code");
    PrimitiveJassType REAL = new RealJassType("real", RealJassValue.ZERO);
    PrimitiveJassType BOOLEAN = new PrimitiveJassType("boolean", BooleanJassValue.FALSE);
    PrimitiveJassType NOTHING = new PrimitiveJassType("nothing",
            JassReturnNothingStatement.RETURN_NOTHING_NOTICE);

    <TYPE> TYPE visit(JassTypeVisitor<TYPE> visitor);

    String getName(); // used for error messages

    boolean isAssignableFrom(JassType value);

    boolean isNullable();

    JassValue getNullValue();
}
