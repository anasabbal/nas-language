package nas.inter.think.nas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nas.inter.think.nas.TokenType.*;

public class Scanner {

    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

    public Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens(){
        while (!isAtEnd()){
            // we are at the beginning od the next lexeme
            start = current;
            scanToken();
        }
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }
    private boolean isAtEnd(){
        return current >= source.length();
    }
    private void scanToken(){
        char c = advance();

        switch (c){
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '/':
                if(match('/'))
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd())
                        advance();
                addToken(SLASH);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                line++;
                break;
            case 'o':
                if(match('r'))
                    addToken(OR);
                break;
            case '"': string(); break;
            default:
                if(isDigit(c))
                    number();
                else if(isAlpha(c))
                    identifier();
                else
                    Nas.error(line, "Unexpected character.");
                    break;
        }
    }

    private void identifier(){
        while (isAlphaNumeric(peek()))
            advance();

        String text = source.substring(start, current);
        TokenType type = keywords.get(text);

        if(type == null)
            type = IDENTIFIER;
        addToken(type);
    }
    private boolean isAlpha(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c == '_');
    }
    private boolean isAlphaNumeric(char c){
        return isAlpha(c) || isDigit(c);
    }

    private char peek() {
        if(!isAtEnd())
            return '\0';
        return source.charAt(current);
    }
    private void number(){
        while (isDigit(peek()))
            advance();
        // Look for a fractional part
        if(peek() == '.' && isDigit(peekNext())){
            // Consume the "."
            advance();
            while (isDigit(peek()))
                advance();
        }
        addToken(NUMBER, Double.parseDouble(source.substring(start, current)));
    }

    private char peekNext() {
        if(current + 1 >= source.length())
            return '\0';
        return source.charAt(current + 1);
    }

    private void string(){
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n')
                line++;
            advance();
        }
        if(isAtEnd()){
            Nas.error(line, "Unterminated string .");
            return;
        }
        // The closing
        advance();

        // Trim the surrounding quotes
        String value = source.substring(start + 1, current - 1);
        addToken(STRING, value);
    }
    
    private boolean isDigit(char c){
        return c >= '0' && c <= '9';
    }

    private boolean match(char expected) {
        if(isAtEnd())
            return false;
        if(source.charAt(current) != expected)
            return false;
        current++;
        return true;
    }

    // The advance() method consumes the next character in the source file and returns it
    private void addToken(TokenType type){
        addToken(type, null);
    }
    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
    // Where advance() is for input, addToken() is for output. It grabs the text of the current lexeme and creates a new token for it
    private char advance() {
        return source.charAt(current++);
    }
}
