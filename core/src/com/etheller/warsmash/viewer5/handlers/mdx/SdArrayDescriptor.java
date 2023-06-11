package com.etheller.warsmash.viewer5.handlers.mdx;

public interface SdArrayDescriptor<TYPE> {
    SdArrayDescriptor<Object> GENERIC = Object[]::new;
    SdArrayDescriptor<float[]> FLOAT_ARRAY = float[][]::new;
    SdArrayDescriptor<long[]> LONG_ARRAY = long[][]::new;

    TYPE[] create(int size);
}
