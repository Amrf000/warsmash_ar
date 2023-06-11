package com.etheller.interpreter.ast.function;

import com.etheller.interpreter.ast.scope.GlobalScope;
import com.etheller.interpreter.ast.scope.TriggerExecutionScope;
import com.etheller.interpreter.ast.value.JassValue;

import java.util.List;

public interface JassFunction {
    JassValue call(List<JassValue> arguments, GlobalScope globalScope, TriggerExecutionScope triggerScope);
}
