package com.etheller.warsmash.fdfparser;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;

public class TestFDFParserBuilder implements FDFParserBuilder {

    public TestFDFParserBuilder(final BaseErrorListener errorListener) {
    }

    @Override
    public FDFParser build(final String path) {
        FDFLexer lexer;
        try {
            lexer = new FDFLexer(CharStreams.fromFileName(path));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return new FDFParser(new CommonTokenStream(lexer));
    }
}
