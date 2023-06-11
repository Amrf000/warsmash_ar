package com.etheller.interpreter.ast;

import com.etheller.interpreter.JassLexer;
import com.etheller.interpreter.JassParser;
import com.etheller.interpreter.ast.function.JassFunction;
import com.etheller.interpreter.ast.scope.GlobalScope;
import com.etheller.interpreter.ast.scope.TriggerExecutionScope;
import com.etheller.interpreter.ast.value.JassValue;
import com.etheller.interpreter.ast.value.visitor.StringJassValueVisitor;
import com.etheller.interpreter.ast.visitors.JassProgramVisitor;
import org.antlr.v4.runtime.*;

import java.util.Collections;
import java.util.List;

public class JassRunner {
    public static final boolean REPORT_SYNTAX_ERRORS = true;

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: <JassFiles> [<AdditionaFile>...]");
            return;
        }
        final JassProgramVisitor jassProgramVisitor = new JassProgramVisitor();
        jassProgramVisitor.getJassNativeManager().createNative("BJDebugMsg", (arguments, globalScope, triggerScope) -> {
            for (final JassValue argument : arguments) {
                System.out.println(argument.visit(StringJassValueVisitor.getInstance()));
            }
            return null;
        });
        for (final String arg : args) {
            try {
                jassProgramVisitor.setCurrentFileName(arg);
                final JassLexer lexer = new JassLexer(CharStreams.fromFileName(arg));
                final JassParser parser = new JassParser(new CommonTokenStream(lexer));
                parser.addErrorListener(new BaseErrorListener() {
                    @Override
                    public void syntaxError(final Recognizer<?, ?> recognizer, final Object offendingSymbol,
                                            final int line, final int charPositionInLine, final String msg,
                                            final RecognitionException e) {
                        if (!REPORT_SYNTAX_ERRORS) {
                            return;
                        }

                        String sourceName = recognizer.getInputStream().getSourceName();
                        if (!sourceName.isEmpty()) {
                            sourceName = String.format("%s:%d:%d: ", sourceName, line, charPositionInLine);
                        }

                        System.err.println(sourceName + "line " + line + ":" + charPositionInLine + " " + msg);
                    }
                });
                jassProgramVisitor.visit(parser.program());
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        jassProgramVisitor.getGlobals().getFunctionByName("main").call(Collections.emptyList(),
                jassProgramVisitor.getGlobals(), null);
    }

}
