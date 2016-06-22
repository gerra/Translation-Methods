import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

/**
 * Created by root on 23.03.16.
 */
public class LexicalAnalyzer {
    enum Token {
        COMMA, POINTER, NAME, SEMICOLON, END;

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private InputStream is;
    private int curChar;
    private int curPos;
    private Token curToken;

    LexicalAnalyzer(InputStream is) throws Parser.ParseException {
        this.is = is;
        curPos = 0;
        getNextChar();
    }

    private boolean isBlank(int c) {
        return Character.isSpaceChar(c) || c == '\n';
    }

    private boolean isVarNameStart(int c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isVarNamePart(int c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    private void skipSpace() throws Parser.ParseException {
        if (isBlank(curChar)) {
            while (isBlank(curChar)) {
                getNextChar();
            }
//            curToken = Token.SPACE;
        }
    }

    public void processNextToken() throws Parser.ParseException {
       skipSpace();
        if (curChar == '*') {
            while (curChar == '*' || Character.isSpaceChar(curChar)) {
                getNextChar();
            }
            curToken = Token.POINTER;
        } else if (curChar == ',') {
            getNextChar();
            curToken = Token.COMMA;
        } else if (isVarNameStart(curChar)) {
            while (isVarNamePart(curChar)) {
                getNextChar();
            }
            curToken = Token.NAME;
        } else if (curChar == ';') {
            getNextChar();
            curToken = Token.SEMICOLON;
        } else if (curChar == -1) {
            curToken = Token.END;
        } else {
            throw new Parser.ParseException("Illegal character " + (char) curChar, curPos);
        }
    }

    private void getNextChar() throws Parser.ParseException {
        curPos++;
        try {
            curChar = is.read();
        } catch (IOException e) {
            throw new Parser.ParseException(e.getMessage(), curPos);
        }
    }

    public Token getCurToken() {
        System.out.println(curToken);
        return curToken;
    }

    public int getCurPos() {
        return curPos;
    }
}
