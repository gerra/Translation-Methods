import java.io.InputStream;

/**
 * Created by root on 24.03.16.
 */
public class Parser {
    public static class ParseException extends java.text.ParseException {

        /**
         * Constructs a ParseException with the specified detail message and
         * offset.
         * A detail message is a String that describes this particular exception.
         *
         * @param s           the detail message
         * @param errorOffset the position where the error is found while parsing.
         */
        public ParseException(String s, int errorOffset) {
            super(s, errorOffset);
        }

        @Override
        public String getMessage() {
            return super.getMessage() + getErrorOffset();
        }
    }

    LexicalAnalyzer lexicalAnalyzer;

    private Tree O() throws ParseException {
        LexicalAnalyzer.Token token = lexicalAnalyzer.getCurToken();
        if (token == LexicalAnalyzer.Token.END) {
            return new Tree("O");
        }
        if (token != LexicalAnalyzer.Token.NAME) {
            throw new ParseException("Type name expected at position ", lexicalAnalyzer.getCurPos());
        }
        lexicalAnalyzer.processNextToken();
        Tree k = K();
        lexicalAnalyzer.processNextToken();
        token = lexicalAnalyzer.getCurToken();
        if (token != LexicalAnalyzer.Token.END) {
            throw new ParseException("END expected at position ", lexicalAnalyzer.getCurPos());
        }
        return new Tree("O", new Tree("T"), k);
    }

    private Tree K() throws ParseException {
        LexicalAnalyzer.Token token = lexicalAnalyzer.getCurToken();
        if (token == LexicalAnalyzer.Token.POINTER) {
            lexicalAnalyzer.processNextToken();
            token = lexicalAnalyzer.getCurToken();
            if (token != LexicalAnalyzer.Token.NAME) {
                throw new ParseException("Name expected at position", lexicalAnalyzer.getCurPos());
            }
            lexicalAnalyzer.processNextToken();
            return new Tree("K", new Tree("P"), new Tree("T"), L());
        } else if (token == LexicalAnalyzer.Token.NAME) {
            lexicalAnalyzer.processNextToken();
            return new Tree("K", new Tree("T"), L());
        } else {
            throw new ParseException("Pointer or name expected at position", lexicalAnalyzer.getCurPos());
        }
    }

    private Tree L() throws ParseException {
        LexicalAnalyzer.Token token = lexicalAnalyzer.getCurToken();
        if (token == LexicalAnalyzer.Token.COMMA) {
            lexicalAnalyzer.processNextToken();
            Tree k = K();
            token = lexicalAnalyzer.getCurToken();
            if (token != LexicalAnalyzer.Token.SEMICOLON) {
                throw new ParseException("; expected at position ", lexicalAnalyzer.getCurPos());
            }
            lexicalAnalyzer.processNextToken();
            return new Tree("W\'", new Tree(","), k, new Tree(";"));
        } else if (token == LexicalAnalyzer.Token.SEMICOLON) {
            lexicalAnalyzer.processNextToken();
            return new Tree("W\'", new Tree(";"));
        } else {
            throw new ParseException("Comma or semicolon expected at position ", lexicalAnalyzer.getCurPos());
        }
    }

    Tree parse(InputStream is) throws ParseException {
        lexicalAnalyzer = new LexicalAnalyzer(is);
        lexicalAnalyzer.processNextToken();
        return O();
    }
}
