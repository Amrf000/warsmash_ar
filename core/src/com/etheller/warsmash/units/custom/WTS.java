package com.etheller.warsmash.units.custom;

public interface WTS {
    WTS DO_NOTHING = key -> "TRIGSTR_" + key;

    String get(int key);
}
