package com.etheller.warsmash.viewer5.handlers;

import com.etheller.warsmash.viewer5.HandlerResource;
import com.etheller.warsmash.viewer5.ModelViewer;

import java.util.List;

public abstract class ResourceHandler {
    public ResourceHandler handler;
    public boolean load;
    public List<String[]> extensions;

    public abstract boolean load(ModelViewer modelViewer);

    public abstract HandlerResource<?> construct(ResourceHandlerConstructionParams params);
}
