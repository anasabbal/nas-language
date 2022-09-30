package nas.inter.think.nas;

public class Token {

    private final TokenType type;
    private final String lexeme;
    private final Object literal;
    private final int line;

    public Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    // TODO: 30/9/2022

    @Override
    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}
