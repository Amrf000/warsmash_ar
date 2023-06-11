package com.etheller.interpreter.ast.scope.trigger;

public interface RemovableTriggerEvent {
    RemovableTriggerEvent DO_NOTHING = () -> {
    };

    void remove();
}
