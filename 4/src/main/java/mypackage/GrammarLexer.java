package mypackage;

import java.io.IOException;
import java.io.InputStream;

public class GrammarLexer {
    enum TokenType {
        OR(new char[] {'|'}, "|", false),
        QUOTEDSTRING(new char[] {'\''}, "'.+?'", true),
        ACTION(new char[] {'{'}, "\\{(?s).+?\\}", true),
        BRACKETTEDVARS(new char[] {'['}, "\\[(?s).+?\\]", true),
        COLON(new char[] {':'}, ":", false),
        SEMICOLON(new char[] {';'}, ";", false),
        RULENAME(new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}, "[a-z][a-zA-Z0-9]*", true),
        TOKENNAME(new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'}, "[A-Z][a-zA-Z0-9]*", true),
        DOLLAR(new char[] {'@'}, "@", false),
        EQUAL(new char[] {'='}, "=", false),
        LBRACKET(new char[] {'('}, "(", false),
        RBRACKET(new char[] {')'}, ")", false),
        PLUS(new char[] {'+'}, "+", false),
        STAR(new char[] {'*'}, "*", false),
        QUESTION(new char[] {'?'}, "?", false),
        POINT(new char[] {'.'}, ".", false),
        END(new char[] {}, "", false);

        char[] firstSymbols;
        boolean isRegex;
        String regex;

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
    TokenType[] tokenTypes;

    public GrammarLexer(InputStream is) throws IOException {
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
        System.out.println(Character.valueOf((char)curChar) + " " + curPos);
        throw new IOException("token not exists");
    }

    public void getNextChar() throws IOException {
        curChar = is.read();
        curPos++;
    }
}
