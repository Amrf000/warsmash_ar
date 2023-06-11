package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.TextJustify;
import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetTextJustifyFieldVisitor implements FrameDefinitionFieldVisitor<TextJustify> {
    public static final GetTextJustifyFieldVisitor INSTANCE = new GetTextJustifyFieldVisitor();

    @Override
    public TextJustify accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public TextJustify accept(final TextJustifyFrameDefinitionField field) {
        return field.getValue();
    }

    @Override
    public TextJustify accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
