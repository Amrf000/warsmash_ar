package com.etheller.warsmash.util;

public interface StringBundle {
    StringBundle EMPTY = string -> string;

    String getString(String string);
}
