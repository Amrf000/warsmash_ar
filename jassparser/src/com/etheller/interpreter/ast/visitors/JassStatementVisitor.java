package com.etheller.interpreter.ast.visitors;

import com.etheller.interpreter.JassBaseVisitor;
import com.etheller.interpreter.JassParser.*;
import com.etheller.interpreter.ast.debug.DebuggingJassStatement;
import com.etheller.interpreter.ast.statement.*;
import com.etheller.interpreter.ast.util.JassSettings;

import java.util.ArrayList;
import java.util.List;

public class JassStatementVisitor extends JassBaseVisitor<JassStatement> {
    private final ArgumentExpressionHandler argumentExpressionHandler;
    private final JassTypeVisitor jassTypeVisitor;

    public JassStatementVisitor(final ArgumentExpressionHandler argumentExpressionHandler,
                                final JassTypeVisitor jassTypeVisitor) {
        this.argumentExpressionHandler = argumentExpressionHandler;
        this.jassTypeVisitor = jassTypeVisitor;
    }

    private static JassStatement wrap(final int lineNo, final JassStatement statement) {
        if (JassSettings.DEBUG) {
            return new DebuggingJassStatement(lineNo, statement);
        } else {
            return statement;
        }
    }

    @Override
    public JassStatement visitCallStatement(final CallStatementContext ctx) {
        try {
            return wrap(ctx.getStart().getLine(), new JassCallStatement(ctx.functionExpression().ID().getText(),
                    this.argumentExpressionHandler.argumentsVisitor.visit(ctx.functionExpression().argsList())));
        } catch (final Exception exc) {
            throw new RuntimeException(ctx.getText(), exc);
        }
    }

    @Override
    public JassStatement visitSetStatement(final SetStatementContext ctx) {
        return wrap(ctx.getStart().getLine(), new JassSetStatement(ctx.ID().getText(),
                this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression())));
    }

    @Override
    public JassStatement visitReturnStatement(final ReturnStatementContext ctx) {
        return wrap(ctx.getStart().getLine(),
                new JassReturnStatement(this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression())));
    }

    @Override
    public JassStatement visitReturnNothingStatement(final ReturnNothingStatementContext ctx) {
        return wrap(ctx.getStart().getLine(), new JassReturnNothingStatement());
    }

    @Override
    public JassStatement visitExitWhenStatement(final ExitWhenStatementContext ctx) {
        return wrap(ctx.getStart().getLine(),
                new JassExitWhenStatement(this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression())));
    }

    @Override
    public JassStatement visitIfElseIfStatement(final IfElseIfStatementContext ctx) {
        final List<JassStatement> thenStatements = new ArrayList<>();
        for (final StatementContext statementCtx : ctx.statements().statement()) {
            thenStatements.add(visit(statementCtx));
        }
        final JassStatement elseIfTail = visit(ctx.ifStatementPartial());
        return wrap(ctx.getStart().getLine(), new JassIfElseIfStatement(
                this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression()), thenStatements, elseIfTail));
    }

    @Override
    public JassStatement visitIfElseStatement(final IfElseStatementContext ctx) {
        final List<JassStatement> thenStatements = new ArrayList<>();
        for (final StatementContext statementCtx : ctx.statements(0).statement()) {
            thenStatements.add(visit(statementCtx));
        }
        final List<JassStatement> elseStatements = new ArrayList<>();
        for (final StatementContext statementCtx : ctx.statements(1).statement()) {
            elseStatements.add(visit(statementCtx));
        }
        return wrap(ctx.getStart().getLine(),
                new JassIfElseStatement(this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression()),
                        thenStatements, elseStatements));
    }

    @Override
    public JassStatement visitLoopStatement(final LoopStatementContext ctx) {
        final List<JassStatement> statements = new ArrayList<>();
        for (final StatementContext statementCtx : ctx.statements().statement()) {
            statements.add(visit(statementCtx));
        }
        return wrap(ctx.getStart().getLine(), new JassLoopStatement(statements));
    }

    @Override
    public JassStatement visitSimpleIfStatement(final SimpleIfStatementContext ctx) {
        final List<JassStatement> thenStatements = new ArrayList<>();
        for (final StatementContext statementCtx : ctx.statements().statement()) {
            thenStatements.add(visit(statementCtx));
        }
        return wrap(ctx.getStart().getLine(), new JassIfStatement(
                this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression()), thenStatements));
    }

    @Override
    public JassStatement visitArrayedAssignmentStatement(final ArrayedAssignmentStatementContext ctx) {
        return wrap(ctx.getStart().getLine(),
                new JassArrayedAssignmentStatement(ctx.ID().getText(),
                        this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression(0)),
                        this.argumentExpressionHandler.expressionVisitor.visit(ctx.expression(1))));
    }

    @Override
    public JassStatement visitBasicLocal(final BasicLocalContext ctx) {
        return wrap(ctx.getStart().getLine(),
                new JassLocalStatement(ctx.ID().getText(), this.jassTypeVisitor.visit(ctx.type())));
    }

    @Override
    public JassStatement visitDefinitionLocal(final DefinitionLocalContext ctx) {
        return wrap(ctx.getStart().getLine(),
                new JassLocalDefinitionStatement(ctx.ID().getText(), this.jassTypeVisitor.visit(ctx.type()),
                        this.argumentExpressionHandler.expressionVisitor.visit(ctx.assignTail().expression())));
    }

    @Override
    public JassStatement visitDebugStatement(final DebugStatementContext ctx) {
        final JassStatement stmt = visit(ctx.statement());
        if (JassSettings.DEBUG) {
            return stmt;
        }
        // TODO this is not performant, and currently only implemented to support the
        // functionality of the debug keyword.
        // Eventually "JassDoNothingStatement" class should be deleted.
        return new JassDoNothingStatement();
    }

    public void setCurrentFileName(final String jassFile) {
    }
}
