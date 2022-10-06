package nas.inter.think.nas;

import java.util.List;

public abstract class Expression {

    interface Visitor<R> {
        R visitAssignExpr(Assign expr);
        R visitBinaryExpr(Binary expr);
        R visitCallExpr(Call expr);
        R visitGetExpr(Get expr);
        R visitGroupingExpr(Grouping expr);
        R visitLiteralExpr(Literal expr);
        R visitLogicalExpr(Logical expr);
        R visitSetExpr(Set expr);
        R visitSuperExpr(Super expr);
        R visitThisExpr(This expr);
        R visitUnaryExpr(Unary expr);
        R visitVariableExpr(Variable expr);
    }
    static class This extends Expression {
        final Token keyword;
        This(Token keyword) {
            this.keyword = keyword;
        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitThisExpr(this);
        }
    }
    static class Unary extends Expression {
        final Token operator;
        final Expression right;
        Unary(Token operator, Expression right) {
            this.operator = operator;
            this.right = right;
        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }
    }
    static class Variable extends Expression {
        final Token name;
        Variable(Token name) {
            this.name = name;
        }
        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariableExpr(this);
        }
    }
    static class Super extends Expression{
        final Token keyword;
        final Token method;

        public Super(Token keyword, Token method) {
            this.keyword = keyword;
            this.method = method;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSuperExpr(this);
        }
    }
    static class Set extends Expression{
        final Expression object;
        final Token name;
        final Expression value;

        public Set(Expression object, Token name, Expression value) {
            this.object = object;
            this.name = name;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitSetExpr(this);
        }
    }
    static class Logical extends Expression{
        final Expression left;
        final Token operator;
        final Expression right;

        public Logical(Expression left, Token operator, Expression right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }
    }
    static class Literal extends Expression{
        final Object value;

        public Literal(Object value) {
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }
    static class Grouping extends Expression{
        final Expression expression;

        public Grouping(Expression expression) {
            this.expression = expression;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }
    }
    static class Get extends Expression{
        final Expression object;
        final Token name;

        public Get(Expression object, Token name) {
            this.object = object;
            this.name = name;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitGetExpr(this);
        }
    }
    static class Call extends Expression{
        final Expression expression;
        final Token paren;
        final List<Expression> arguments;

        public Call(Expression expression, Token paren, List<Expression> arguments) {
            this.expression = expression;
            this.paren = paren;
            this.arguments = arguments;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitCallExpr(this);
        }
    }
    static class Assign extends Expression{
        final Token name;
        final Expression value;
        Assign(Token name, Expression value){
            this.name = name;
            this.value = value;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
    }

    static class Binary extends Expression{

        final Expression left;
        final Expression right;
        final Token operator;

        public Binary(Expression left, Expression right, Token operator) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }

        @Override
        <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }
    abstract <R> R accept(Visitor<R> visitor);
}
