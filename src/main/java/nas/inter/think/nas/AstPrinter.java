package nas.inter.think.nas;

public class AstPrinter implements Expression.Visitor<String>{

    // As you can see, it implements the visitor interface. That means we need visit methods for each of the expression types we have so far.
    String print(Expression expression){
        return expression.accept(this);
    }

    // Literal expressions are easy—they convert the value to a string with a little check to handle Java’s null standing in for NAS’s nil
    @Override
    public String visitBinaryExpr(Expression.Binary expr) {
        return parenthesize(expr.operator.lexeme,
                expr.left, expr.right);
    }
    @Override
    public String visitAssignExpr(Expression.Assign expr) {
        return null;
    }
    @Override
    public String visitCallExpr(Expression.Call expr) {
        return null;
    }

    @Override
    public String visitGetExpr(Expression.Get expr) {
        return null;
    }

    @Override
    public String visitGroupingExpr(Expression.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expression.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitLogicalExpr(Expression.Logical expr) {
        return null;
    }

    @Override
    public String visitSetExpr(Expression.Set expr) {
        return null;
    }

    @Override
    public String visitSuperExpr(Expression.Super expr) {
        return null;
    }

    @Override
    public String visitThisExpr(Expression.This expr) {
        return null;
    }

    @Override
    public String visitUnaryExpr(Expression.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitVariableExpr(Expression.Variable expr) {
        return null;
    }

    // The other expressions have subexpressions, so they use this parenthesize() helper method
    private String parenthesize(String name, Expression...expressions){
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);

        for(Expression expression: expressions){
            builder.append(" ");
            builder.append(expression.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }
}
