import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class BigCalcProgVisitorImpl extends BigCalcProgBaseVisitor<BigDecimal> {
    private final Map<String, BigDecimal> variables = new HashMap<>();

    @Override
    public BigDecimal visitProgram(BigCalcProgParser.ProgramContext ctx) {
        BigDecimal lastResult = null;
        for (BigCalcProgParser.StatementContext statement : ctx.statement()) {
            lastResult = visit(statement);
        }
        return lastResult;
    }

    @Override
    public BigDecimal visitAssignmentStatement(BigCalcProgParser.AssignmentStatementContext ctx) {
        String variable = ctx.Variable().getText();
        BigDecimal value = visit(ctx.expression());
        variables.put(variable, value);
        return value;
    }

    @Override
    public BigDecimal visitExpressionStatement(BigCalcProgParser.ExpressionStatementContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcProgParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        return ctx.op.getText().equals("*") ? left.multiply(right) : left.divide(right, 10, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal visitAddSub(BigCalcProgParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        return ctx.op.getText().equals("+") ? left.add(right) : left.subtract(right);
    }

    @Override
    public BigDecimal visitParenExpr(BigCalcProgParser.ParenExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitVariable(BigCalcProgParser.VariableContext ctx) {
        return variables.getOrDefault(ctx.getText(), BigDecimal.ZERO);
    }

    @Override
    public BigDecimal visitNum(BigCalcProgParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }

}
