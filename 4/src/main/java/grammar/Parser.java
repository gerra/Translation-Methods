package grammar;

import java.io.*;
import java.util.*;

import static grammar.Lexer.TokenType.*;

    import java.util.HashMap;
public class Parser {
    Lexer lexer;

    Lexer.Token token;
    
  HashMap<String, Integer> memory = new HashMap<String, Integer>();


    Parser(InputStream is) throws IOException {
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

    Tree parseSTR_59() throws IOException {
        if (token.tokenType != STR_59) {
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

    Tree parseSTR_61() throws IOException {
        if (token.tokenType != STR_61) {
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

    static class TokenRuleResultNAME {
        Tree tree;
        String text;
    }

    TokenRuleResultNAME parseNAME() throws IOException {
        if (token.tokenType != NAME) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultNAME res = new TokenRuleResultNAME();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class RuleResultmain {
        Tree tree;
    }

    RuleResultmain parsemain() throws IOException {
        switch (token.tokenType) {
            case NAME: {
                Tree resTree = new Tree("main");

                RuleResultmain res = new RuleResultmain();
                RuleResultassignments r0 = parseassignments();
                resTree.addChild(r0.tree);
                
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultassignments {
        Tree tree;
    }

    RuleResultassignments parseassignments() throws IOException {
        switch (token.tokenType) {
            case NAME: {
                Tree resTree = new Tree("assignments");

                RuleResultassignments res = new RuleResultassignments();
                RuleResultassignment r0 = parseassignment();
                resTree.addChild(r0.tree);
                Tree tree0 = parseSTR_59();
                resTree.addChild(tree0);

                RuleResultassignments r1 = parseassignments();
                resTree.addChild(r1.tree);
                
                res.tree = resTree;
                return res;
            }
            default: {
                RuleResultassignments res = new RuleResultassignments();
                
                res.tree = new Tree("eps");
                return res;
            }
        }
    }

    static class RuleResultassignment {
        Tree tree;
        String assignmentAsString;
    }

    RuleResultassignment parseassignment() throws IOException {
        switch (token.tokenType) {
            case NAME: {
                Tree resTree = new Tree("assignment");

                RuleResultassignment res = new RuleResultassignment();
                TokenRuleResultNAME t0 = parseNAME();
                resTree.addChild(t0.tree);

                Tree tree0 = parseSTR_61();
                resTree.addChild(tree0);

                RuleResultexpr r1 = parseexpr();
                resTree.addChild(r1.tree);
                
            String varName = t0.text;
            int varValue = r1.val;
            memory.put(varName, varValue);
            res.assignmentAsString = varName + " = " + varValue;
            System.out.println(res.assignmentAsString);
        
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultexpr {
        Tree tree;
        int val;
    }

    RuleResultexpr parseexpr() throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case STR_40: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NAME: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP r1 = parseexprP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultexprP {
        Tree tree;
        int val;
    }

    RuleResultexprP parseexprP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_43: {
                Tree resTree = new Tree("exprP");

                RuleResultexprP res = new RuleResultexprP();
                Tree tree0 = parseSTR_43();
                resTree.addChild(tree0);

                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP e = parseexprP(i + r0.val);
                resTree.addChild(e.tree);
                 res.val = e.val; 
                res.tree = resTree;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("exprP");

                RuleResultexprP res = new RuleResultexprP();
                Tree tree0 = parseSTR_45();
                resTree.addChild(tree0);

                RuleResultterm r0 = parseterm();
                resTree.addChild(r0.tree);
                RuleResultexprP e = parseexprP(i - r0.val);
                resTree.addChild(e.tree);
                 res.val = e.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                RuleResultexprP res = new RuleResultexprP();
                 res.val = i; 
                res.tree = new Tree("eps");
                return res;
            }
        }
    }

    static class RuleResultterm {
        Tree tree;
        int val;
    }

    RuleResultterm parseterm() throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case STR_40: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NAME: {
                Tree resTree = new Tree("term");

                RuleResultterm res = new RuleResultterm();
                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP r1 = parsetermP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResulttermP {
        Tree tree;
        int val;
    }

    RuleResulttermP parsetermP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_42: {
                Tree resTree = new Tree("termP");

                RuleResulttermP res = new RuleResulttermP();
                Tree tree0 = parseSTR_42();
                resTree.addChild(tree0);

                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP e = parsetermP(i * r0.val);
                resTree.addChild(e.tree);
                 res.val = e.val; 
                res.tree = resTree;
                return res;
            }
            case STR_47: {
                Tree resTree = new Tree("termP");

                RuleResulttermP res = new RuleResulttermP();
                Tree tree0 = parseSTR_47();
                resTree.addChild(tree0);

                RuleResultfact r0 = parsefact();
                resTree.addChild(r0.tree);
                RuleResulttermP e = parsetermP(i / r0.val);
                resTree.addChild(e.tree);
                 res.val = e.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                RuleResulttermP res = new RuleResulttermP();
                 res.val = i; 
                res.tree = new Tree("eps");
                return res;
            }
        }
    }

    static class RuleResultfact {
        Tree tree;
        int val;
    }

    RuleResultfact parsefact() throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                Tree tree0 = parseSTR_45();
                resTree.addChild(tree0);

                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                 res.val = (-1) * r0.val; 
                res.tree = resTree;
                return res;
            }
            case STR_40: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                 res.val = r0.val; 
                res.tree = resTree;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                 res.val = r0.val; 
                res.tree = resTree;
                return res;
            }
            case NAME: {
                Tree resTree = new Tree("fact");

                RuleResultfact res = new RuleResultfact();
                RuleResultlast r0 = parselast();
                resTree.addChild(r0.tree);
                 res.val = r0.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultlast {
        Tree tree;
        int val;
    }

    RuleResultlast parselast() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("last");

                RuleResultlast res = new RuleResultlast();
                RuleResultatom r0 = parseatom();
                resTree.addChild(r0.tree);
                RuleResultlastP r1 = parselastP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("last");

                RuleResultlast res = new RuleResultlast();
                RuleResultatom r0 = parseatom();
                resTree.addChild(r0.tree);
                RuleResultlastP r1 = parselastP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            case NAME: {
                Tree resTree = new Tree("last");

                RuleResultlast res = new RuleResultlast();
                RuleResultatom r0 = parseatom();
                resTree.addChild(r0.tree);
                RuleResultlastP r1 = parselastP(r0.val);
                resTree.addChild(r1.tree);
                 res.val = r1.val; 
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultlastP {
        Tree tree;
        int val;
    }

    RuleResultlastP parselastP(int i) throws IOException {
        switch (token.tokenType) {
            case STR_94: {
                Tree resTree = new Tree("lastP");

                RuleResultlastP res = new RuleResultlastP();
                Tree tree0 = parseSTR_94();
                resTree.addChild(tree0);

                RuleResultlast e = parselast();
                resTree.addChild(e.tree);
                 res.val = (int)Math.pow((double)i, (double)e.val); 
                res.tree = resTree;
                return res;
            }
            default: {
                RuleResultlastP res = new RuleResultlastP();
                 res.val = i; 
                res.tree = new Tree("eps");
                return res;
            }
        }
    }

    static class RuleResultatom {
        Tree tree;
        int val;
    }

    RuleResultatom parseatom() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("atom");

                RuleResultatom res = new RuleResultatom();
                Tree tree0 = parseSTR_40();
                resTree.addChild(tree0);

                RuleResultexpr r0 = parseexpr();
                resTree.addChild(r0.tree);
                Tree tree1 = parseSTR_41();
                resTree.addChild(tree1);

                 res.val = r0.val; 
                res.tree = resTree;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("atom");

                RuleResultatom res = new RuleResultatom();
                TokenRuleResultNUM t0 = parseNUM();
                resTree.addChild(t0.tree);

                 res.val = Integer.parseInt(t0.text); 
                res.tree = resTree;
                return res;
            }
            case NAME: {
                Tree resTree = new Tree("atom");

                RuleResultatom res = new RuleResultatom();
                TokenRuleResultNAME t0 = parseNAME();
                resTree.addChild(t0.tree);

                 res.val = memory.get(t0.text); 
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

}

