package homework3;

import java.io.*;
import java.util.*;

import static homework3.Lexer.TokenType.*;

    import java.lang.StringBuilder;
    import misc.Utils;
public class Parser {
    Lexer lexer;

    Lexer.Token token;
    

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

    Tree parseSTR_123() throws IOException {
        if (token.tokenType != STR_123) {
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

    Tree parseSTR_125() throws IOException {
        if (token.tokenType != STR_125) {
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

    static class TokenRuleResultVAR {
        Tree tree;
        String text;
    }

    TokenRuleResultVAR parseVAR() throws IOException {
        if (token.tokenType != VAR) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultVAR res = new TokenRuleResultVAR();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class TokenRuleResultPRINT {
        Tree tree;
        String text;
    }

    TokenRuleResultPRINT parsePRINT() throws IOException {
        if (token.tokenType != PRINT) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultPRINT res = new TokenRuleResultPRINT();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class TokenRuleResultLACTION {
        Tree tree;
        String text;
    }

    TokenRuleResultLACTION parseLACTION() throws IOException {
        if (token.tokenType != LACTION) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultLACTION res = new TokenRuleResultLACTION();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class TokenRuleResultIF {
        Tree tree;
        String text;
    }

    TokenRuleResultIF parseIF() throws IOException {
        if (token.tokenType != IF) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultIF res = new TokenRuleResultIF();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class TokenRuleResultELSE {
        Tree tree;
        String text;
    }

    TokenRuleResultELSE parseELSE() throws IOException {
        if (token.tokenType != ELSE) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultELSE res = new TokenRuleResultELSE();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    static class TokenRuleResultSEMICOLON {
        Tree tree;
        String text;
    }

    TokenRuleResultSEMICOLON parseSEMICOLON() throws IOException {
        if (token.tokenType != SEMICOLON) {
            throw new IOException("UnexpectedToken");
        }
        TokenRuleResultSEMICOLON res = new TokenRuleResultSEMICOLON();
        Tree resTree = new Tree(token.tokenType.name());
        res.tree = resTree;
        res.text = token.str;
        token = lexer.processNextToken();
        return res;
    }

    public static class RuleResultprog {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultprog parseprog(String begin, String end) throws IOException {
        switch (token.tokenType) {
            case IF: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  1);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = begin + b.s + "\n" + end;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  1);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = begin + b.s + "\n" + end;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case PRINT: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  1);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = begin + b.s + "\n" + end;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_123: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  1);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = begin + b.s + "\n" + end;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                Tree resTree = new Tree("prog");

                RuleResultprog res = new RuleResultprog();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  1);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = begin + b.s + "\n" + end;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
        }
    }

    public static class RuleResultbody {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultbody parsebody(String begin, String end, int shift) throws IOException {
        switch (token.tokenType) {
            case IF: {
                Tree resTree = new Tree("body");

                RuleResultbody res = new RuleResultbody();
                String asText = "";
                RuleResultexprs e = parseexprs(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = "\n" + e.s + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("body");

                RuleResultbody res = new RuleResultbody();
                String asText = "";
                RuleResultexprs e = parseexprs(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = "\n" + e.s + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case PRINT: {
                Tree resTree = new Tree("body");

                RuleResultbody res = new RuleResultbody();
                String asText = "";
                RuleResultexprs e = parseexprs(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = "\n" + e.s + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_123: {
                Tree resTree = new Tree("body");

                RuleResultbody res = new RuleResultbody();
                String asText = "";
                RuleResultexprs e = parseexprs(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = "\n" + e.s + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultbody res = new RuleResultbody();
                
        res.s = "";
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultifP {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultifP parseifP(String begin, String end, int shift) throws IOException {
        switch (token.tokenType) {
            case ELSE: {
                Tree resTree = new Tree("ifP");

                RuleResultifP res = new RuleResultifP();
                String asText = "";
                TokenRuleResultELSE el = parseELSE();
                resTree.addChild(el.tree);

                asText += el.text;
                RuleResultexprs ex = parseexprs(begin,  end,  shift + 1);
                resTree.addChild(ex.tree);
                asText += ex.text;
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = " else " + begin + "\n" + ex.s + "\n" + Utils.genTab(shift) + end + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case IF: {
                Tree resTree = new Tree("ifP");

                RuleResultifP res = new RuleResultifP();
                String asText = "";
                RuleResultelif i = parseelif();
                resTree.addChild(i.tree);
                asText += i.text;
                RuleResultcondition c = parsecondition();
                resTree.addChild(c.tree);
                asText += c.text;
                RuleResultexprs e = parseexprs(begin,  end,  shift + 1);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultifP ifp = parseifP(begin,  end,  shift);
                resTree.addChild(ifp.tree);
                asText += ifp.text;
                
        res.s = " else if " + c.s + " " + begin + "\n" + e.s + "\n" + Utils.genTab(shift) + end + ifp.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("ifP");

                RuleResultifP res = new RuleResultifP();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case PRINT: {
                Tree resTree = new Tree("ifP");

                RuleResultifP res = new RuleResultifP();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_123: {
                Tree resTree = new Tree("ifP");

                RuleResultifP res = new RuleResultifP();
                String asText = "";
                RuleResultbody b = parsebody(begin,  end,  shift);
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.s = b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultifP res = new RuleResultifP();
                
    res.s = "";
    
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultelif {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultelif parseelif() throws IOException {
        switch (token.tokenType) {
            case ELSE: {
                Tree resTree = new Tree("elif");

                RuleResultelif res = new RuleResultelif();
                String asText = "";
                TokenRuleResultELSE e = parseELSE();
                resTree.addChild(e.tree);

                asText += e.text;
                TokenRuleResultIF i = parseIF();
                resTree.addChild(i.tree);

                asText += i.text;
                
        res.s = e.text + " " + i.text;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case IF: {
                Tree resTree = new Tree("elif");

                RuleResultelif res = new RuleResultelif();
                String asText = "";
                TokenRuleResultIF i = parseIF();
                resTree.addChild(i.tree);

                asText += i.text;
                
        res.s = i.text;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultcondition {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultcondition parsecondition() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("condition");

                RuleResultcondition res = new RuleResultcondition();
                String asText = "";
                Tree tree0 = parseSTR_40();
                resTree.addChild(tree0);

                asText += "(";
                RuleResultcondition c = parsecondition();
                resTree.addChild(c.tree);
                asText += c.text;
                Tree tree1 = parseSTR_41();
                resTree.addChild(tree1);

                asText += ")";
                
        res.s = c.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case LACTION: {
                Tree resTree = new Tree("condition");

                RuleResultcondition res = new RuleResultcondition();
                String asText = "";
                TokenRuleResultLACTION l = parseLACTION();
                resTree.addChild(l.tree);

                asText += l.text;
                RuleResultoperand o1 = parseoperand();
                resTree.addChild(o1.tree);
                asText += o1.text;
                RuleResultoperand o2 = parseoperand();
                resTree.addChild(o2.tree);
                asText += o2.text;
                
        res.s = "(" + o1.s + " " + l.text + " " + o2.s + ")";
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultexprs {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultexprs parseexprs(String begin, String end, int shift) throws IOException {
        switch (token.tokenType) {
            case IF: {
                Tree resTree = new Tree("exprs");

                RuleResultexprs res = new RuleResultexprs();
                String asText = "";
                RuleResultexpr e = parseexpr(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultexprEnd r0 = parseexprEnd();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.s = e.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("exprs");

                RuleResultexprs res = new RuleResultexprs();
                String asText = "";
                RuleResultexpr e = parseexpr(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultexprEnd r0 = parseexprEnd();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.s = e.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case PRINT: {
                Tree resTree = new Tree("exprs");

                RuleResultexprs res = new RuleResultexprs();
                String asText = "";
                RuleResultexpr e = parseexpr(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultexprEnd r0 = parseexprEnd();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.s = e.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_123: {
                Tree resTree = new Tree("exprs");

                RuleResultexprs res = new RuleResultexprs();
                String asText = "";
                Tree tree0 = parseSTR_123();
                resTree.addChild(tree0);

                asText += "{";
                RuleResultexpr e = parseexpr(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultexprsP ep = parseexprsP(begin,  end,  shift);
                resTree.addChild(ep.tree);
                asText += ep.text;
                Tree tree1 = parseSTR_125();
                resTree.addChild(tree1);

                asText += "}";
                
        res.s = e.s + ep.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultexprsP {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultexprsP parseexprsP(String begin, String end, int shift) throws IOException {
        switch (token.tokenType) {
            case SEMICOLON: {
                Tree resTree = new Tree("exprsP");

                RuleResultexprsP res = new RuleResultexprsP();
                String asText = "";
                TokenRuleResultSEMICOLON t0 = parseSEMICOLON();
                resTree.addChild(t0.tree);

                asText += t0.text;
                RuleResultexpr e = parseexpr(begin,  end,  shift);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultexprsP ep = parseexprsP(begin,  end,  shift);
                resTree.addChild(ep.tree);
                asText += ep.text;
                
        res.s = "\n" + e.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                Tree resTree = new Tree("exprsP");

                RuleResultexprsP res = new RuleResultexprsP();
                String asText = "";
                RuleResultexprEnd r0 = parseexprEnd();
                resTree.addChild(r0.tree);
                asText += r0.text;
                
        res.s = "";
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
        }
    }

    public static class RuleResultexprEnd {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultexprEnd parseexprEnd() throws IOException {
        switch (token.tokenType) {
            case SEMICOLON: {
                Tree resTree = new Tree("exprEnd");

                RuleResultexprEnd res = new RuleResultexprEnd();
                String asText = "";
                TokenRuleResultSEMICOLON div = parseSEMICOLON();
                resTree.addChild(div.tree);

                asText += div.text;
                
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                RuleResultexprEnd res = new RuleResultexprEnd();
                
                res.tree = new Tree("eps");
                res.text = "";
                return res;
            }
        }
    }

    public static class RuleResultexpr {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultexpr parseexpr(String begin, String end, int shift) throws IOException {
        switch (token.tokenType) {
            case IF: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                TokenRuleResultIF i = parseIF();
                resTree.addChild(i.tree);

                asText += i.text;
                RuleResultcondition c = parsecondition();
                resTree.addChild(c.tree);
                asText += c.text;
                RuleResultexprs e = parseexprs(begin,  end,  shift + 1);
                resTree.addChild(e.tree);
                asText += e.text;
                RuleResultifP ifp = parseifP(begin,  end,  shift);
                resTree.addChild(ifp.tree);
                asText += ifp.text;
                
        res.s = Utils.genTab(shift) + i.text + " " + c.s +" " + begin + "\n" + e.s + "\n" + Utils.genTab(shift) + end + ifp.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                RuleResultassignment a = parseassignment();
                resTree.addChild(a.tree);
                asText += a.text;
                
        res.s = Utils.genTab(shift) + a.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case PRINT: {
                Tree resTree = new Tree("expr");

                RuleResultexpr res = new RuleResultexpr();
                String asText = "";
                TokenRuleResultPRINT p = parsePRINT();
                resTree.addChild(p.tree);

                asText += p.text;
                RuleResultoperand o = parseoperand();
                resTree.addChild(o.tree);
                asText += o.text;
                
         res.s = Utils.genTab(shift) + p.text + "(" + o.s + ");";
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultassignment {
        Tree tree;
        public String s;
        String text;
    }

    public RuleResultassignment parseassignment() throws IOException {
        switch (token.tokenType) {
            case VAR: {
                Tree resTree = new Tree("assignment");

                RuleResultassignment res = new RuleResultassignment();
                String asText = "";
                TokenRuleResultVAR v = parseVAR();
                resTree.addChild(v.tree);

                asText += v.text;
                Tree tree0 = parseSTR_61();
                resTree.addChild(tree0);

                asText += "=";
                RuleResultoperand o = parseoperand();
                resTree.addChild(o.tree);
                asText += o.text;
                
        res.s = v.text + " = " + o.s + ";";
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultoperand {
        Tree tree;
        public boolean isSum;
        public String s;
        String text;
    }

    public RuleResultoperand parseoperand() throws IOException {
        switch (token.tokenType) {
            case STR_40: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                Tree tree0 = parseSTR_40();
                resTree.addChild(tree0);

                asText += "(";
                RuleResultoperand o = parseoperand();
                resTree.addChild(o.tree);
                asText += o.text;
                Tree tree1 = parseSTR_41();
                resTree.addChild(tree1);

                asText += ")";
                
        res.isSum = o.isSum;
        res.s = o.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case VAR: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                TokenRuleResultVAR v = parseVAR();
                resTree.addChild(v.tree);

                asText += v.text;
                
        res.isSum = false;
        res.s = v.text;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case NUM: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                TokenRuleResultNUM n = parseNUM();
                resTree.addChild(n.tree);

                asText += n.text;
                
        res.isSum = false;
        res.s = n.text;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_45: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                RuleResultoperation op = parseoperation();
                resTree.addChild(op.tree);
                asText += op.text;
                
        res.isSum = op.isSum;
        res.s = op.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_43: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                RuleResultoperation op = parseoperation();
                resTree.addChild(op.tree);
                asText += op.text;
                
        res.isSum = op.isSum;
        res.s = op.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_47: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                RuleResultoperation op = parseoperation();
                resTree.addChild(op.tree);
                asText += op.text;
                
        res.isSum = op.isSum;
        res.s = op.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_42: {
                Tree resTree = new Tree("operand");

                RuleResultoperand res = new RuleResultoperand();
                String asText = "";
                RuleResultoperation op = parseoperation();
                resTree.addChild(op.tree);
                asText += op.text;
                
        res.isSum = op.isSum;
        res.s = op.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultoperation {
        Tree tree;
        public boolean isSum;
        public String s;
        String text;
    }

    public RuleResultoperation parseoperation() throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("operation");

                RuleResultoperation res = new RuleResultoperation();
                String asText = "";
                RuleResultaCTION2 ac2 = parseaCTION2();
                resTree.addChild(ac2.tree);
                asText += ac2.text;
                RuleResultoperand a = parseoperand();
                resTree.addChild(a.tree);
                asText += a.text;
                RuleResultoperand b = parseoperand();
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.isSum = true;
        res.s = a.s + ac2.text + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_43: {
                Tree resTree = new Tree("operation");

                RuleResultoperation res = new RuleResultoperation();
                String asText = "";
                RuleResultaCTION2 ac2 = parseaCTION2();
                resTree.addChild(ac2.tree);
                asText += ac2.text;
                RuleResultoperand a = parseoperand();
                resTree.addChild(a.tree);
                asText += a.text;
                RuleResultoperand b = parseoperand();
                resTree.addChild(b.tree);
                asText += b.text;
                
        res.isSum = true;
        res.s = a.s + ac2.text + b.s;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_47: {
                Tree resTree = new Tree("operation");

                RuleResultoperation res = new RuleResultoperation();
                String asText = "";
                RuleResultaCTION1 ac1 = parseaCTION1();
                resTree.addChild(ac1.tree);
                asText += ac1.text;
                RuleResultoperand a = parseoperand();
                resTree.addChild(a.tree);
                asText += a.text;
                RuleResultoperand b = parseoperand();
                resTree.addChild(b.tree);
                asText += b.text;
                
        String aVal = "";
        if (a.isSum == true) aVal = "(" + a.s + ")";
        else aVal = a.s;
        String bVal = "";
        if (b.isSum == true) bVal = "(" + b.s + ")";
        else bVal = b.s;
        res.s = aVal + ac1.text + bVal;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_42: {
                Tree resTree = new Tree("operation");

                RuleResultoperation res = new RuleResultoperation();
                String asText = "";
                RuleResultaCTION1 ac1 = parseaCTION1();
                resTree.addChild(ac1.tree);
                asText += ac1.text;
                RuleResultoperand a = parseoperand();
                resTree.addChild(a.tree);
                asText += a.text;
                RuleResultoperand b = parseoperand();
                resTree.addChild(b.tree);
                asText += b.text;
                
        String aVal = "";
        if (a.isSum == true) aVal = "(" + a.s + ")";
        else aVal = a.s;
        String bVal = "";
        if (b.isSum == true) bVal = "(" + b.s + ")";
        else bVal = b.s;
        res.s = aVal + ac1.text + bVal;
    
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultaCTION1 {
        Tree tree;
        String text;
    }

    public RuleResultaCTION1 parseaCTION1() throws IOException {
        switch (token.tokenType) {
            case STR_47: {
                Tree resTree = new Tree("aCTION1");

                RuleResultaCTION1 res = new RuleResultaCTION1();
                String asText = "";
                Tree tree0 = parseSTR_47();
                resTree.addChild(tree0);

                asText += "/";
                
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_42: {
                Tree resTree = new Tree("aCTION1");

                RuleResultaCTION1 res = new RuleResultaCTION1();
                String asText = "";
                Tree tree0 = parseSTR_42();
                resTree.addChild(tree0);

                asText += "*";
                
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            default: {
                throw new IOException("unexpected token");
            }
        }
    }

    public static class RuleResultaCTION2 {
        Tree tree;
        String text;
    }

    public RuleResultaCTION2 parseaCTION2() throws IOException {
        switch (token.tokenType) {
            case STR_45: {
                Tree resTree = new Tree("aCTION2");

                RuleResultaCTION2 res = new RuleResultaCTION2();
                String asText = "";
                Tree tree0 = parseSTR_45();
                resTree.addChild(tree0);

                asText += "-";
                
                res.tree = resTree;
                res.text = asText;
                return res;
            }
            case STR_43: {
                Tree resTree = new Tree("aCTION2");

                RuleResultaCTION2 res = new RuleResultaCTION2();
                String asText = "";
                Tree tree0 = parseSTR_43();
                resTree.addChild(tree0);

                asText += "+";
                
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

