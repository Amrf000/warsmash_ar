package com.etheller.warsmash.viewer5.handlers.mdx;

import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericGroup {
    public final List<Integer> objects;

    public GenericGroup() {
        this.objects = new ArrayList<>(); // TODO IntArrayList
    }

    public abstract void render(MdxComplexInstance instance, Matrix4 mvp);

}
