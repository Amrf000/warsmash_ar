package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.Vector2Definition;
import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetVector2FieldVisitor implements FrameDefinitionFieldVisitor<Vector2Definition> {
    public static final GetVector2FieldVisitor INSTANCE = new GetVector2FieldVisitor();

    @Override
    public Vector2Definition accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final Vector2FrameDefinitionField field) {
        return field.getValue();
    }

    @Override
    public Vector2Definition accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public Vector2Definition accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
