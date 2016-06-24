package homework3;

import java.io.IOException;
import java.io.InputStream;

public class Lexer {
    enum TokenType {
        STR_40(new char[] {'('}, "(", false),
        STR_41(new char[] {')'}, ")", false),
        STR_42(new char[] {'*'}, "*", false),
        STR_123(new char[] {'{'}, "{", false),
        STR_43(new char[] {'+'}, "+", false),
        STR_125(new char[] {'}'}, "}", false),
        STR_61(new char[] {'='}, "=", false),
        STR_45(new char[] {'-'}, "-", false),
        STR_47(new char[] {'/'}, "/", false),
        NUM(new char[] {'0','1','2','3','4','5','6','7','8','9'}, "[0-9]+", true),
        VAR(new char[] {'$'}, "[$]{1}[a-zA-Z_][a-zA-Z0-9_]*", true),
        PRINT(new char[] {'p'}, "print", true),
        LACTION(new char[] {'!','<','=','>'}, "(<|>|==|!=|<=|>=)", true),
        IF(new char[] {'i'}, "if", true),
        ELSE(new char[] {'e'}, "else", true),
        SEMICOLON(new char[] {';'}, ";", true),
        END(new char[] {}, "", false);
        char[] firstSymbols;
        String regex;
        boolean isRegex;
        TokenType(char[] firstSymbols, String regex, boolean isRegex) {
            this.firstSymbols = firstSymbols;
            this.regex = regex;
            this.isRegex = isRegex;
        }
    }

    public class Token {
        public TokenType tokenType;
        public String str;

        public Token(TokenType tokenType, String str) {
            this.tokenType = tokenType;
            this.str = str;
        }
    }

    InputStream is;
    int curChar;
    int curPos;
    Token curToken;
    TokenType[] tokenTypes;

    public Lexer(InputStream is) throws IOException {
        this.is = is;
        curPos = 0;
        this.tokenTypes = TokenType.values();
        getNextChar();
    }

    public Token processNextToken() throws IOException {
        while (Character.isSpaceChar(curChar) || curChar == '\n') {
            getNextChar();
        }
        if (curChar == -1) {
            return new Token(TokenType.END, "");
        }
        for (TokenType tokenType : tokenTypes) {
            for (char firstSymbol : tokenType.firstSymbols) {
                if (curChar != firstSymbol) {
                    continue;
                }
                String tmp = "" + firstSymbol;
                if (tokenType.isRegex) {
                    while (!tmp.matches(tokenType.regex)) {
                        getNextChar();
                        tmp += (char) curChar;
                    }
                    while (tmp.matches(tokenType.regex)) {
                        getNextChar();
                        tmp += (char) curChar;
                    }
                    tmp = tmp.substring(0, tmp.length() - 1);
                    return new Token(tokenType, tmp);
                } else {
                    while (!tmp.equals(tokenType.regex)) {
                        getNextChar();
                        tmp += (char) curChar;
                    }
                    getNextChar();
                    return new Token(tokenType, tmp);
                }
            }
        }
        throw new IOException("unknown token starts with" + (char) curChar);
    }

    public void getNextChar() throws IOException {
        curChar = is.read();
        curPos++;
    }
}
