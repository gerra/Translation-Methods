package homework2;

import java.io.*;
import java.util.*;

import static homework2.Lexer.TokenType.*;


    import java.util.HashMap;
public class Parser {
    Lexer lexer;

    Lexer.Token token;
    
  HashMap<String, Integer> memory = new HashMap<String, Integer>();


    public Parser(InputStream is) throws IOException {
        lexer = new Lexer(is);
        token = lexer.processNextToken();
    }
    Tree parseSTR_40() throws IOException {
        if (token.tokenType != STR_40) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_41() throws IOException {
        if (token.tokenType != STR_41) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_42() throws IOException {
        if (token.tokenType != STR_42) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_43() throws IOException {
        if (token.tokenType != STR_43) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_45() throws IOException {
        if (token.tokenType != STR_45) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_94() throws IOException {
        if (token.tokenType != STR_94) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_47() throws IOException {
        if (token.tokenType != STR_47) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    static class TokenRuleResultNUM {
        Tree tree;
        String text;
    }

    TokenRuleResultNUM parseNUM() throws IOException {
        if (token.tokenType != NUM) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultNUM res = new TokenRuleResultNUM();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    public static class RuleResultprog {
        Tree tree;
        String text;
    }

    public RuleResultprog parseprog() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultexpr r0 = parseexpr();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        System.out.println(r0.val);
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultexpr r0 = parseexpr();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        System.out.println(r0.val);
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultexpr r0 = parseexpr();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        System.out.println(r0.val);
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultprog res = new RuleResultprog();
                
        System.out.println("empty");
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultexpr {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultexpr parseexpr() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultexprP {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultexprP parseexprP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("exprP");

                RuleResultexprP res = new RuleResultexprP();
                String asText = "";
                Tree tree0 = parseSTR_45();
                resTree.addChild(tree0);

                asText += "-";
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultexprP e = parseexprP(i - r0.val);
                resTree.addChild(e.tree);
                asText += e.text;
                
        res.val = e.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_43: {
                Tree resTree = new Tree("exprP");

                RuleResultexprP res = new RuleResultexprP();
                String asText = "";
                Tree tree0 = parseSTR_43();
                resTree.addChild(tree0);

                asText += "+";
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultexprP e = parseexprP(i + r0.val);
                resTree.addChild(e.tree);
                asText += e.text;
                
        res.val = e.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultexprP res = new RuleResultexprP();
                
        res.val = i;
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultterm {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultterm parseterm() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                String asText = "";
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                String asText = "";
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                String asText = "";
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResulttermP {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResulttermP parsetermP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_47: {
                Tree resTree = new Tree("termP");

                RuleResulttermP res = new RuleResulttermP();
                String asText = "";
                Tree tree0 = parseSTR_47();
                resTree.addChild(tree0);

                asText += "/";
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResulttermP e = parsetermP(i / r0.val);
                resTree.addChild(e.tree);
                asText += e.text;
                
        res.val = e.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_42: {
                Tree resTree = new Tree("termP");

                RuleResulttermP res = new RuleResulttermP();
                String asText = "";
                Tree tree0 = parseSTR_42();
                resTree.addChild(tree0);

                asText += "*";
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResulttermP e = parsetermP(i * r0.val);
                resTree.addChild(e.tree);
                asText += e.text;
                
        res.val = e.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResulttermP res = new RuleResulttermP();
                
        res.val = i;
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultfact {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultfact parsefact() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                String asText = "";
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.val = r0.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                String asText = "";
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.val = r0.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                String asText = "";
                Tree tree0 = parseSTR_45();
                resTree.addChild(tree0);

                asText += "-";
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.val = (-1) * r0.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultlast {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultlast parselast() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("last");

                RuleResultlast res = new RuleResultlast();
                String asText = "";
                RuleResultatom r0 = parseatom();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultlastP r1 = parselastP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("last");

                RuleResultlast res = new RuleResultlast();
                String asText = "";
                RuleResultatom r0 = parseatom();
                resTree.addChild(r0.tree);
                asText += r0.text;
                RuleResultlastP r1 = parselastP(r0.val);
                resTree.addChild(r1.tree);
                asText += r1.text;
                
        res.val = r1.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultlastP {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultlastP parselastP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_94: {
                Tree resTree = new Tree("lastP");

                RuleResultlastP res = new RuleResultlastP();
                String asText = "";
                Tree tree0 = parseSTR_94();
                resTree.addChild(tree0);

                asText += "^";
                RuleResultexpr e = parseexpr();
                resTree.addChild(e.tree);
                asText += e.text;
                
        res.val = (int)Math.pow((double)i, (double)e.val);
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultlastP res = new RuleResultlastP();
                
        res.val = i;
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultatom {
        Tree tree;
        public int val;
        String text;
    }

    public RuleResultatom parseatom() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("atom");

                RuleResultatom res = new RuleResultatom();
                String asText = "";
                Tree tree0 = parseSTR_40();
                resTree.addChild(tree0);

                asText += "(";
                RuleResultexpr r0 = parseexpr();
                resTree.addChild(r0.tree);
                asText += r0.text;
                Tree tree1 = parseSTR_41();
                resTree.addChild(tree1);

                asText += ")";
                
        res.val = r0.val;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("atom");

                RuleResultatom res = new RuleResultatom();
                String asText = "";
                TokenRuleResultNUM t0 = parseNUM();
                resTree.addChild(t0.tree);

                asText += t0.text;
                
        res.val = Integer.parseInt(t0.text);
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

}

