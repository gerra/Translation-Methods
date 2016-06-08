package test;

import java.io.*;
import java.util.*;

import static test.Lexer.TokenType.*;
public class Parser {
    Lexer lexer;

    Lexer.Token token;
    

    Parser(InputStream is) throws IOException {
        lexer = new Lexer(is);
        token = lexer.processNextToken();
    }
    Tree parseSTR_59() throws IOException {
        if (token.tokenType != STR_59) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    Tree parseSTR_44() throws IOException {
        if (token.tokenType != STR_44) {
            throw new IOException("UnexpectedToken");
        }
        Tree resTree = new Tree(token.tokenType.name());
        token = lexer.processNextToken();
        return resTree;
    }

    static class TokenRuleResultStr {
        Tree tree;
        String text;
    }

    TokenRuleResultStr parseStr() throws IOException {
        if (token.tokenType != Str) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultStr res = new TokenRuleResultStr();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class RuleResultdecls {
        Tree tree;
    }

    RuleResultdecls parsedecls() throws IOException {
        switch (token.tokenType) {
            case Str: {
                Tree resTree = new Tree("decls");

                RuleResultdecls res = new RuleResultdecls();
                RuleResultdecl r0 = parsedecl();
                resTree.addChild(r0.tree);
                RuleResultdecls r1 = parsedecls();
                resTree.addChild(r1.tree);
                
                res.tree = resTree;
                return res;
            }
            default: {
                RuleResultdecls res = new RuleResultdecls();
                
                res.tree = new Tree("eps");
                return res;
            }
        }
    }

    static class RuleResultdecl {
        Tree tree;
    }

    RuleResultdecl parsedecl() throws IOException {
        switch (token.tokenType) {
            case Str: {
                Tree resTree = new Tree("decl");

                RuleResultdecl res = new RuleResultdecl();
                TokenRuleResultStr t0 = parseStr();
                resTree.addChild(t0.tree);

                RuleResultvars z = parsevars();
                resTree.addChild(z.tree);
                Tree tree0 = parseSTR_59();
                resTree.addChild(tree0);

                System.out.println(t0.text + " " + z.str + ";" + z.bool);
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultvars {
        Tree tree;
        String str;
        boolean bool;
    }

    RuleResultvars parsevars() throws IOException {
        switch (token.tokenType) {
            case Str: {
                Tree resTree = new Tree("vars");

                RuleResultvars res = new RuleResultvars();
                TokenRuleResultStr t0 = parseStr();
                resTree.addChild(t0.tree);

                RuleResultvarsE r1 = parsevarsE();
                resTree.addChild(r1.tree);
                res.str = t0.text; bool = true;
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    static class RuleResultvarsE {
        Tree tree;
    }

    RuleResultvarsE parsevarsE() throws IOException {
        switch (token.tokenType) {
            case STR_44: {
                Tree resTree = new Tree("varsE");

                RuleResultvarsE res = new RuleResultvarsE();
                Tree tree0 = parseSTR_44();
                resTree.addChild(tree0);

                TokenRuleResultStr t0 = parseStr();
                resTree.addChild(t0.tree);

                RuleResultvarsE r1 = parsevarsE();
                resTree.addChild(r1.tree);
                
                res.tree = resTree;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

}

