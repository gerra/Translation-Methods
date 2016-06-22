package mypackage;

import java.io.IOException;
import java.io.InputStream;

public class GrammarLexer {
    enum Token {
        STR_59(new char[] {';'}, ";", false),
        STR_59(new char[] {';'}, ";", false),
        TOK_21685669(new char[] {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','_','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'}, "[_a-zA-Z][_a-zA-Z0-9]*", true),
        END(new char[] {}, "");
        char[] firstSymbols;
        String regex;
        TokenType(char[] firstSymbols, String regex) {
            this.firstSymbols = firstSymbols;
            this.regex = regex;
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
        throw new IOException("token not exists");
    }

    public void getNextChar() throws IOException {
        curChar = is.read();
        curPos++;
    }
}
