package mypackage;

import sun.text.resources.ro.CollationData_ro;

import java.io.*;
import java.util.*;

import static mypackage.GrammarLexer.TokenType.*;

public class GrammarParser {
    GrammarLexer lexer;

    GrammarLexer.Token token;

    String imports = "";
    String members = "";
    List<Rule> rules = new ArrayList<>();
    List<TokenRule> tokenRules = new ArrayList<>();
    Set<String> innerStrings = new HashSet<>();

    GrammarParser(InputStream is) throws IOException {
        lexer = new GrammarLexer(is);
        token = lexer.processNextToken();
    }

    void parseAfterDollar() throws IOException {
        if (token.str.equals("header")) {
            token = lexer.processNextToken();
            if (token.tokenType != ACTION) {
                throw new IOException("Action after @include expected");
            }
            imports = token.str.substring(1, token.str.length() - 1);
            token = lexer.processNextToken();
        } else if (token.str.equals("members")) {
            token = lexer.processNextToken();
            if (token.tokenType != ACTION) {
                throw new IOException("Action after @members expected");
            }
            members = token.str.substring(1, token.str.length() - 1);
            token = lexer.processNextToken();
        } else {
            throw new IOException("import or members expected");
        }
    }

    private void parseRuleTakeList(Rule rule) throws IOException {
        if (token.tokenType == BRACKETTEDVARS) {
            String takeList = token.str.substring(1, token.str.length() - 1);
            String[] takesSplitted = takeList.split(",");
            for (String take : takesSplitted) {
                String[] var = take.split("[ \r\t\n]+");
                int len = var.length;
                rule.addTake(new Rule.Var(var[len-2], var[len-1]));
            }
            token = lexer.processNextToken();
        }
    }

    private void parseRuleArgs(Rule rule) throws IOException {
        if (token.tokenType == BRACKETTEDVARS) {
            String takeList = token.str.substring(1, token.str.length() - 1);
            String[] takesSplitted = takeList.split(",");
            for (String take : takesSplitted) {
                rule.addTake(new Rule.Var("?", take));
            }
            token = lexer.processNextToken();
        }
    }

    void parseRuleReturns(Rule rule) throws IOException {
        if (token.str.equals("returns")) {
            token = lexer.processNextToken();
            if (token.tokenType != BRACKETTEDVARS) {
                throw new IOException("what i should return?");
            }
            String returns = token.str.substring(1, token.str.length() - 1);
            String[] returnsSplitted = returns.split(",");
            for (String returnSplitted : returnsSplitted) {
                String[] varStuff = returnSplitted.split("[ \r\t\n]+");
                String varType = null;
                String varName = null;
                for (String stuff : varStuff) {
                    if (stuff.isEmpty()) {
                        continue;
                    }
                    if (varType != null) {
                        varName = stuff;
                    } else {
                        varType = stuff;
                    }
                }
                rule.addReturn(new Rule.Var(varType, varName));
            }
            token = lexer.processNextToken();
        }
    }

    void processRule(String ruleName) throws IOException {
        Rule rule = new Rule(ruleName);

        parseRuleTakeList(rule);
        parseRuleReturns(rule);

        if (token.tokenType != COLON) {
            throw new IOException(": expected after ruleName");
        }
        token = lexer.processNextToken();

        Rule.RuleDescr ruleDescr = new Rule.RuleDescr();
        int id = 0;
        while (token.tokenType != SEMICOLON) {
            if (token.tokenType == RULENAME) {
                String assignName = null;
                GrammarLexer.Token assignedToken = token;
                token = lexer.processNextToken();
                if (token.tokenType == EQUAL) {
                    assignName = assignedToken.str;
                    assignedToken = lexer.processNextToken();
                    if (assignedToken.tokenType == RULENAME) {
                        Rule.RulePartAssignR t = new Rule.RulePartAssignR(assignName, new Rule(assignedToken.str));
                        token = lexer.processNextToken();
                        parseRuleArgs(t.rule);
                        ruleDescr.addRulePart(t);
                    } else if (assignedToken.tokenType == TOKENNAME) {
                        Rule.RulePartAssignT t = new Rule.RulePartAssignT(assignName, new Token(assignedToken.str));
                        ruleDescr.addRulePart(t);
                        token = lexer.processNextToken();
                    } else {
                        throw new IOException("after =");
                    }
                } else {
                    Rule partRule = new Rule(assignedToken.str);
                    parseRuleArgs(partRule);
                    Rule.RulePart rulePart;
                    rulePart = new Rule.RulePartAssignR("r" + id++, partRule);
                    ruleDescr.addRulePart(rulePart);
                }

            } else if (token.tokenType == TOKENNAME) {
                ruleDescr.addRulePart(new Rule.RulePartAssignT("t" + id++, new Token(token.str)));
                token = lexer.processNextToken();
            } else if (token.tokenType == QUOTEDSTRING) {
                String str = token.str.substring(1, token.str.length() - 1);
                innerStrings.add(str);
                ruleDescr.addRulePart(new Rule.RulePartStr(str));
                token = lexer.processNextToken();
            } else if (token.tokenType == ACTION) {
                ruleDescr.setAction(token.str.substring(1, token.str.length() - 1));
                token = lexer.processNextToken();
            } else if (token.tokenType == OR) {
                rule.addRuleDescr(ruleDescr);
                ruleDescr = new Rule.RuleDescr();
                token = lexer.processNextToken();
                id = 0;
            } else {
                throw new IOException("unexpected token");
            }
        }
        rule.addRuleDescr(ruleDescr);
        token = lexer.processNextToken();
        rules.add(rule);
    }

    void processTokenRule(String tokenRuleName) throws IOException {
        if (token.tokenType != COLON) {
            throw new IOException(": expected after tokenName");
        }
        token = lexer.processNextToken();
        if (token.tokenType != QUOTEDSTRING) {
            throw new IOException("regex in quotes expected");
        }
        TokenRule tokenRule = new TokenRule(tokenRuleName);
        String regex = token.str.substring(1, token.str.length() - 1);
        tokenRule.setRegex(regex);
        token = lexer.processNextToken();
        if (token.tokenType != SEMICOLON) {
            throw new IOException("; expected at the end of the token rule");
        }
        token = lexer.processNextToken();
        tokenRules.add(tokenRule);
    }

    void parse() throws IOException {
        do {
//            System.out.println(token.tokenType + (token.tokenType == GrammarLexer.TokenType.RULENAME ? " " + token.str : ""));
            if (token.tokenType == DOLLAR) {
                token = lexer.processNextToken();
                parseAfterDollar();
            } else if (token.tokenType == RULENAME) {
                String ruleName = token.str;
                token = lexer.processNextToken();
                processRule(ruleName);
            } else if (token.tokenType == TOKENNAME) {
                String tokenRuleName = token.str;
                token = lexer.processNextToken();
                processTokenRule(tokenRuleName);
            }
        } while (token.tokenType != GrammarLexer.TokenType.END);
    }

    ///////////////////////////////////////////////////////

    String packageName;

    File lexerFile;
    File parserFile;
    File treeFile;
    File mainFile;
    Writer lexerWriter;
    Writer parserWriter;
    Writer treeWriter;
    Writer mainWriter;

    int globalSpace = 0;

    Map<String, FirstFollowSet> firsSets = new HashMap<>();
    Map<String, FirstFollowSet> followSets = new HashMap<>();

    String globalSpace() {
        String res = "";
        for (int i = 0; i < globalSpace; i++) {
            res += "    ";
        }
        return res;
    }

    void genTokenTypeEnum() throws IOException {
        lexerWriter.write(globalSpace() + "enum TokenType {\n");
        globalSpace++;
        for (String s : innerStrings) {
            lexerWriter.write(globalSpace() + "STR_" + s.hashCode() + "(new char[] {" + "'" + s.charAt(0) + "'"
                    + "}, \"" + s + "\", false),\n");
        }
        for (TokenRule tokenRule : tokenRules) {
            lexerWriter.write(globalSpace() + tokenRule.name + "(new char[] {"
                    + Utils.getFirstCharsOfRegexAsString(tokenRule.regex) + "}, \"" + tokenRule.regex + "\", true),\n");
        }
        lexerWriter.write(globalSpace() + "END(new char[] {}, \"\", false);\n");
        lexerWriter.write(globalSpace() + "char[] firstSymbols;\n");
        lexerWriter.write(globalSpace() + "String regex;\n");
        lexerWriter.write(globalSpace() + "boolean isRegex;\n");
        lexerWriter.write(globalSpace() + "TokenType(char[] firstSymbols, String regex, boolean isRegex) {\n");
        globalSpace++;
        lexerWriter.write(globalSpace() + "this.firstSymbols = firstSymbols;\n");
        lexerWriter.write(globalSpace() + "this.regex = regex;\n");
        lexerWriter.write(globalSpace() + "this.isRegex = isRegex;\n");
        globalSpace--;
        lexerWriter.write(globalSpace() + "}\n");
        globalSpace--;
        lexerWriter.write(globalSpace() + "}\n");
    }

    void createLexerFile() throws IOException {
        lexerWriter.write("package " + packageName + ";\n\n");
        lexerWriter.write("import java.io.IOException;\n" +
                "import java.io.InputStream;\n\n");
        lexerWriter.write("public class Lexer {\n");
        globalSpace++;
        genTokenTypeEnum();
        lexerWriter.write("\n" +
                "    public class Token {\n" +
                "        public TokenType tokenType;\n" +
                "        public String str;\n" +
                "\n" +
                "        public Token(TokenType tokenType, String str) {\n" +
                "            this.tokenType = tokenType;\n" +
                "            this.str = str;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    InputStream is;\n" +
                "    int curChar;\n" +
                "    int curPos;\n" +
                "    Token curToken;\n" +
                "    TokenType[] tokenTypes;\n" +
                "\n" +
                "    public Lexer(InputStream is) throws IOException {\n" +
                "        this.is = is;\n" +
                "        curPos = 0;\n" +
                "        this.tokenTypes = TokenType.values();\n" +
                "        getNextChar();\n" +
                "    }\n" +
                "\n" +
                "    public Token processNextToken() throws IOException {\n" +
                "        while (Character.isSpaceChar(curChar) || curChar == '\\n') {\n" +
                "            getNextChar();\n" +
                "        }\n" +
                "        if (curChar == -1) {\n" +
                "            return new Token(TokenType.END, \"\");\n" +
                "        }\n" +
                "        for (TokenType tokenType : tokenTypes) {\n" +
                "            for (char firstSymbol : tokenType.firstSymbols) {\n" +
                "                if (curChar != firstSymbol) {\n" +
                "                    continue;\n" +
                "                }\n" +
                "                String tmp = \"\" + firstSymbol;\n" +
                "                if (tokenType.isRegex) {\n" +
                "                    while (!tmp.matches(tokenType.regex)) {\n" +
                "                        getNextChar();\n" +
                "                        tmp += (char) curChar;\n" +
                "                    }\n" +
                "                    while (tmp.matches(tokenType.regex)) {\n" +
                "                        getNextChar();\n" +
                "                        tmp += (char) curChar;\n" +
                "                    }\n" +
                "                    tmp = tmp.substring(0, tmp.length() - 1);\n" +
                "                    return new Token(tokenType, tmp);\n" +
                "                } else {\n" +
                "                    while (!tmp.equals(tokenType.regex)) {\n" +
                "                        getNextChar();\n" +
                "                        tmp += (char) curChar;\n" +
                "                    }\n" +
                "                    getNextChar();\n" +
                "                    return new Token(tokenType, tmp);\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        throw new IOException(\"unknown token starts with\" + (char) curChar);\n" +
                "    }\n" +
                "\n" +
                "    public void getNextChar() throws IOException {\n" +
                "        curChar = is.read();\n" +
                "        curPos++;\n" +
                "    }\n" +
                "}\n");
        globalSpace = 0;
    }

    void genRuleDescr(Rule.RuleDescr ruleDescr) throws IOException {
        Rule rule = ruleDescr.parentRule;
        String ruleResultClass = "RuleResult" + rule.name;
        parserWriter.write(globalSpace() + "Tree resTree = new Tree(\"" + rule.name + "\");\n\n");
        parserWriter.write(globalSpace() + ruleResultClass + " res = new " + ruleResultClass + "();\n");
        parserWriter.write(globalSpace() + "String asText = \"\";\n");
        int treeInd = 0;
        for (int ri = 0; ri < ruleDescr.ruleParts.size(); ri++) {
            Rule.RulePart rulePart = ruleDescr.ruleParts.get(ri);
            if (rulePart instanceof Rule.RulePartStr) {
                Rule.RulePartStr partStr = (Rule.RulePartStr) rulePart;

                parserWriter.write(globalSpace() + "Tree tree" + treeInd + " = parse" + "STR_" + partStr.string.hashCode() + "();\n");
                parserWriter.write(globalSpace() + "resTree.addChild(tree" + treeInd + ");\n\n");
                parserWriter.write(globalSpace() + "asText += \"" + partStr.string + "\";\n");
                treeInd++;
            } else if (rulePart instanceof Rule.RulePartAssignT) {
                Rule.RulePartAssignT partAssignT = (Rule.RulePartAssignT) rulePart;
                Token assignedToken = partAssignT.token;

                parserWriter.write(globalSpace() + "TokenRuleResult" + assignedToken.name + " " + partAssignT.name
                        + " = parse" + assignedToken.name + "();\n");
                parserWriter.write(globalSpace() + "resTree.addChild(" + partAssignT.name + ".tree);\n\n");
                parserWriter.write(globalSpace() + "asText += " + partAssignT.name + ".text;\n");
            } else if (rulePart instanceof Rule.RulePartAssignR) {
                Rule.RulePartAssignR partAssignR = (Rule.RulePartAssignR) rulePart;
                Rule assignedRule = partAssignR.rule;

                String callWith = "";
                for (Rule.Var var : assignedRule.takeList) {
                    if (var != assignedRule.takeList.get(0)) {
                        callWith += ", ";
                    }
                    callWith += var.name;
                }
                callWith = deleteDollars(ruleDescr, ri, callWith);

                parserWriter.write(globalSpace() + "RuleResult" + assignedRule.name + " " + partAssignR.name
                        + " = parse" + assignedRule.name + "(" + callWith + ");\n");
                parserWriter.write(globalSpace() + "resTree.addChild(" + partAssignR.name + ".tree);\n");
                parserWriter.write(globalSpace() + "asText += " + partAssignR.name + ".text;\n");
            }
        }
        parserWriter.write(globalSpace() + deleteDollars(ruleDescr, ruleDescr.ruleParts.size(), ruleDescr.aciton) + "\n");
        parserWriter.write(globalSpace() + "res.tree = resTree;\n");
        parserWriter.write(globalSpace() + "res.text = asText;\n");
        parserWriter.write(globalSpace() + "return res;\n");
    }

    void createParserFile() throws IOException {
        parserWriter.write("package " + packageName + ";\n\n");
        parserWriter.write("import java.io.*;\n" +
                "import java.util.*;\n\n");
        parserWriter.write("import static " + packageName + ".Lexer.TokenType.*;\n");
        parserWriter.write(imports);
        parserWriter.write("public class Parser {\n" +
                "    Lexer lexer;\n" +
                "\n" +
                "    Lexer.Token token;\n");
        globalSpace = 1;
        parserWriter.write(globalSpace() + members + "\n\n");

        parserWriter.write(globalSpace() + "public Parser(InputStream is) throws IOException {\n" +
                "        lexer = new Lexer(is);\n" +
                "        token = lexer.processNextToken();\n" +
                "    }\n");

        for (String string : innerStrings) {
            String tokenName = "STR_" + string.hashCode();
            parserWriter.write(globalSpace() + "Tree parse" + tokenName + "() throws IOException {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "if (token.tokenType != " + tokenName + ") {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "throw new IOException(\"UnexpectedToken\");\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n");
            parserWriter.write(globalSpace() + "Tree resTree = new Tree(token.tokenType.name());\n");
            parserWriter.write(globalSpace() + "token = lexer.processNextToken();\n");
            parserWriter.write(globalSpace() + "return resTree;\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n\n");
        }

        for (TokenRule tokenRule : tokenRules) {
            String tokenRuleResultClass = "TokenRuleResult" + tokenRule.name;
            parserWriter.write(globalSpace() + "static class " + tokenRuleResultClass + " {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "Tree tree;\n");
            parserWriter.write(globalSpace() + "String text;\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n\n");
            parserWriter.write(globalSpace() + tokenRuleResultClass + " parse" + tokenRule.name + "() throws IOException {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "if (token.tokenType != " + tokenRule.name + ") {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "throw new IOException(\"UnexpectedToken\");\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n");
            parserWriter.write(globalSpace() + tokenRuleResultClass + " res = new " + tokenRuleResultClass + "();\n");
            parserWriter.write(globalSpace() + "Tree resTree = new Tree(token.tokenType.name());\n");
            parserWriter.write(globalSpace() + "res.tree = resTree;\n");
            parserWriter.write(globalSpace() + "res.text = token.str;\n");
            parserWriter.write(globalSpace() + "token = lexer.processNextToken();\n");
            parserWriter.write(globalSpace() + "return res;\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n\n");
        }

        for (Rule rule : rules) {
            String ruleResultClass = "RuleResult" + rule.name;
            parserWriter.write(globalSpace() + "public static class " + ruleResultClass + " {\n");
            globalSpace++;
            parserWriter.write(globalSpace() + "Tree tree;\n");
            for (Rule.Var var : rule.returnList) {
                parserWriter.write(globalSpace() + "public " + var.type + " " + var.name + ";\n");
            }
            parserWriter.write(globalSpace() + "String text;\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n\n");
            parserWriter.write(globalSpace() + "public " + ruleResultClass + " parse" + rule.name + "(");
            for (Rule.Var var : rule.takeList) {
                if (var != rule.takeList.get(0)) {
                    parserWriter.write(", ");
                }
                parserWriter.write(var.type + " " + var.name);
            }
            parserWriter.write(") throws IOException {\n");
            globalSpace++;
//            parserWriter.write(globalSpace() + "token = lexer.processNextToken();\n");
            parserWriter.write(globalSpace() + "switch (token.tokenType) {\n");
            globalSpace++;
            for (FirstFollowSet.FirstFollowSetElement element : firsSets.get(rule.name).elements) {
                Rule.RuleDescr ruleDescr = element.descr;
//                Rule.RuleDescr ruleDescr = findRuleDescr(rule, element.t);
                if (ruleDescr.ruleParts.size() == 0) {
                    continue;
                }
                parserWriter.write(globalSpace() + "case " + element.t + ": {\n");
                globalSpace++;
                genRuleDescr(ruleDescr);
                globalSpace--;
                parserWriter.write(globalSpace() + "}\n");
            }
            parserWriter.write(globalSpace() + "default: {\n");
            globalSpace++;
            if (rule.getEpsRuleDescr() == null) {
                parserWriter.write(globalSpace() + "throw new IOException(\"unexpected token\");\n");
            } else {
                Rule.RuleDescr ruleDescr = rule.getEpsRuleDescr();

                while (!ruleDescr.parentRule.name.equals(rule.name)) {
                    ruleDescr = ruleDescr.parentRule.getEpsRuleDescr();
                }

                if (ruleDescr.ruleParts.size() == 0) {
                    parserWriter.write(globalSpace() + ruleResultClass + " res = new " + ruleResultClass + "();\n");
                    parserWriter.write(globalSpace() + deleteDollars(ruleDescr, ruleDescr.ruleParts.size(), ruleDescr.aciton) + "\n");
                    parserWriter.write(globalSpace() + "res.tree = new Tree(\"eps\");\n");
                    parserWriter.write(globalSpace() + "res.text = \"\";\n");
                    parserWriter.write(globalSpace() + "return res;\n");
                } else {
                    genRuleDescr(ruleDescr);
                }
            }
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n");

            globalSpace--;
            parserWriter.write(globalSpace() + "}\n");
            globalSpace--;
            parserWriter.write(globalSpace() + "}\n\n");
        }

        globalSpace--;
        parserWriter.write(globalSpace() + "}\n\n");
    }

    void createTreeFile() throws IOException {
        treeWriter.write("package " + packageName + ";\n\n");
        treeWriter.write("import java.util.Arrays;\n" +
                "import java.util.Collections;\n" +
                "import java.util.List;\n" +
                "import java.util.ArrayList;\n" +
                "\n" +
                "public class Tree {\n" +
                "    String node;\n" +
                "\n" +
                "    List<Tree> children = new ArrayList<>();\n" +
                "\n" +
                "    public Tree(String node, Tree... children) {\n" +
                "        this.node = node;\n" +
                "        this.children = Arrays.asList(children);\n" +
                "    }\n" +
                "\n" +
                "    public Tree(String node) {\n" +
                "        this.node = node;\n" +
                "    }\n" +
                "\n" +
                "    public void addChild(Tree child) {\n" +
                "        children.add(child);\n" +
                "    }\n" +
                "\n" +
                "    private boolean isLeaf() {\n" +
                "        return children == null || children.size() == 0;\n" +
                "    }\n" +
                "\n" +
                "    public String toXml(boolean isRoot) {\n" +
                "        StringBuilder stringBuilder = new StringBuilder();\n" +
                "        if (isRoot) {\n" +
                "            stringBuilder.append(\"<tree>\\n\");\n" +
                "            stringBuilder.append(\"<declarations>\\n\");\n" +
                "            stringBuilder.append(\"<attributeDecl name=\\\"name\\\" type=\\\"String\\\"/>\\n\");\n" +
                "            stringBuilder.append(\"</declarations>\\n\");\n" +
                "        }\n" +
                "        if (isLeaf()) {\n" +
                "            stringBuilder.append(\"<leaf>\\n\");\n" +
                "        } else {\n" +
                "            stringBuilder.append(\"<branch>\\n\");\n" +
                "        }\n" +
                "\n" +
                "        stringBuilder.append(\"<attribute name=\\\"name\\\" value=\\\"\")\n" +
                "                .append(node)\n" +
                "                .append(\"\\\"/>\\n\");\n" +
                "\n" +
                "        if (children != null) {\n" +
                "            for (Tree child : children) {\n" +
                "                stringBuilder.append(child.toXml(false));\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        if (isLeaf()) {\n" +
                "            stringBuilder.append(\"</leaf>\\n\");\n" +
                "        } else {\n" +
                "            stringBuilder.append(\"</branch>\\n\");\n" +
                "        }\n" +
                "\n" +
                "        if (isRoot) {\n" +
                "            stringBuilder.append(\"</tree>\\n\");\n" +
                "        }\n" +
                "        return stringBuilder.toString();\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        String children = this.children == null ? \"\" : \", \\\"children\\\":\" + this.children.toString();\n" +
                "        return \"{\" +\n" +
                "                \"\\\"node\\\":\\\"\" + node + '\\\"' +\n" +
                "                children +\n" +
                "                '}';\n" +
                "    }\n" +
                "}");
    }

    boolean isVarPart(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    String deleteDollars(Rule.RuleDescr ruleDescr, int ri, String toDelete) {
        List<Rule.RulePart> ruleParts = ruleDescr.ruleParts;
        String callWith = "";
        for (int i = 0; i < toDelete.length();) {
            char c = toDelete.charAt(i);
            if (c == '$') {
                i++;
                String varName = "";
                while (i < toDelete.length() && isVarPart(toDelete.charAt(i))) {
                    varName += toDelete.charAt(i);
                    i++;
                }
                char cur = toDelete.charAt(i);
                if (cur == '.') {
                    for (int rj = 0; rj < ri; rj++) {
                        Rule.RulePart prev = ruleParts.get(rj);
                        if (varName.equals(prev.getS1()) || varName.equals(prev.getS2())) {
                            callWith += prev.getS1();
                            break;
                        }
                    }
                } else {
                    boolean found = false;
                    for (Rule.Var var : ruleDescr.parentRule.takeList) {
                        if (var.name.equals(varName)) {
                            found = true;
                            callWith += varName;
                            break;
                        }
                    }
                    if (!found) {
                        callWith += "res." + varName;
                    }
                }
                callWith += cur;
                i++;
            } else {
                callWith += c;
                i++;
            }
        }
        return callWith;
    }

    void createMainFile(String mainRule, String input) throws IOException {
        mainWriter.write("package " + packageName + ";\n\n");
        mainWriter.write(
                "import java.io.FileInputStream;\n" +
                "import java.io.IOException;\n" +
                "\n" +
                "public class Main {\n" +
                "    public static void main(String[] args) throws IOException {\n" +
                "        if (args.length < 1) {\n" +
                "            System.err.println(\"Need test file\");\n" +
                "            System.exit(1);\n" +
                "        }\n" +
                "        Parser parser = new Parser(new FileInputStream(args[0]));\n" +
                "        parser.parse" + mainRule + "("+(input!=null?input:"")+");\n" +
                "    }\n" +
                "}\n");
    }

    void createLexerAndParserFiles(String packageName, String mainRule, String input) {
        String root = "src/main/java/";
        this.packageName = packageName;
        try {
            lexerFile = new File(root + packageName + File.separator + "Lexer.java");
            parserFile = new File(root + packageName + File.separator + "Parser.java");
            treeFile = new File(root + packageName + File.separator + "Tree.java");
            mainFile = new File(root + packageName + File.separator + "Main.java");
            lexerFile.getParentFile().mkdirs();
            lexerFile.createNewFile();
            parserFile.createNewFile();
            treeFile.createNewFile();
            mainFile.createNewFile();
            lexerWriter = new FileWriter(lexerFile);
            parserWriter = new FileWriter(parserFile);
            treeWriter = new FileWriter(treeFile);
            mainWriter = new FileWriter(mainFile);

            List<Rule.RuleDescr> ruleDescrs = new ArrayList<>();
            for (Rule rule : rules) {
                firsSets.put(rule.name, new FirstFollowSet());
                followSets.put(rule.name, new FirstFollowSet());
                ruleDescrs.addAll(rule.ruleDescrs);
            }
            Collections.reverse(ruleDescrs);
            boolean changed;
            do {
                changed = false;
                for (Rule.RuleDescr ruleDescr : ruleDescrs) {
                    FirstFollowSet currentSet = firsSets.get(ruleDescr.parentRule.name);
                    if (ruleDescr.ruleParts.size() > 0) {
                        Rule.RulePart firstPart = ruleDescr.ruleParts.get(0);
                        if (firstPart instanceof Rule.RulePartAssignR) {
                            String name = ((Rule.RulePartAssignR) firstPart).rule.name;
                            changed |= currentSet.merge(firsSets.get(name), ruleDescr);
                        } else if (firstPart instanceof Rule.RulePartAssignT) {
                            String tokenName = ((Rule.RulePartAssignT) firstPart).token.name;
                            currentSet.add(new FirstFollowSet.FirstFollowSetElement(tokenName, null), ruleDescr);
                        } else if (firstPart instanceof Rule.RulePartStr) {
                            FirstFollowSet.FirstFollowSetElement firstSetElement = new FirstFollowSet.FirstFollowSetElement("STR_" + ((Rule.RulePartStr) firstPart).string.hashCode(), null);
                            changed |= currentSet.add(firstSetElement, ruleDescr);
                        }
                    } else {
                        ruleDescr.parentRule.epsRuleDescr = ruleDescr;
                        changed |= currentSet.addEps();
                    }
                }
            } while (changed);

            do {
                changed = false;
                for (Rule.RuleDescr ruleDescr : ruleDescrs) {
                    int curInd = ruleDescr.ruleParts.size() - 2;
                    while (curInd >= 0) {
                        Rule.RulePart rulePart = ruleDescr.ruleParts.get(curInd);
                        if (rulePart instanceof Rule.RulePartAssignR) {
                            String name = ((Rule.RulePartAssignR) rulePart).rule.name;
                            FirstFollowSet currentSet = followSets.get(name);
                            Rule.RulePart nextRulePart = ruleDescr.ruleParts.get(curInd + 1);
                            if (nextRulePart instanceof Rule.RulePartAssignR) {
                                Rule nextRule = ((Rule.RulePartAssignR) nextRulePart).rule;
                                changed |= currentSet.mergeWithoutEps(firsSets.get(nextRule.name), ruleDescr);
                                if (firsSets.get(nextRule.name).hasEps) {
                                    changed |= currentSet.merge(followSets.get(ruleDescr.parentRule.name), ruleDescr);
                                }
                            } else if (nextRulePart instanceof Rule.RulePartAssignT) {
                                String tokenName = ((Rule.RulePartAssignT) nextRulePart).token.name;
                                currentSet.add(new FirstFollowSet.FirstFollowSetElement(tokenName, null), ruleDescr);
                            } else if (nextRulePart instanceof Rule.RulePartStr) {
                                FirstFollowSet.FirstFollowSetElement followSetElement = new FirstFollowSet.FirstFollowSetElement(
                                        "STR_" + ((Rule.RulePartStr) nextRulePart).string.hashCode(), null);
                                changed |= currentSet.add(followSetElement, ruleDescr);
                            }
                        }
                        curInd--;
                    }
                }
            } while (changed);

            do {
                changed = false;
                for (Rule rule : rules) {
                    if (rule.getEpsRuleDescr() == null) {
                        for (Rule.RuleDescr ruleDescr : rule.ruleDescrs) {
                            boolean isEps = true;
                            for (Rule.RulePart rulePart : ruleDescr.ruleParts) {
                                if (rulePart instanceof Rule.RulePartAssignR) {
                                    Rule r = ((Rule.RulePartAssignR) rulePart).rule;
                                    isEps &= r.getEpsRuleDescr() != null;
                                } else if (rulePart instanceof Rule.RulePartAssignT) {
                                    isEps = false;
                                } else if (rulePart instanceof Rule.RulePartStr) {
                                    isEps = false;
                                }
                            }
                            if (isEps) {
                                changed = true;
                                rule.epsRuleDescr = ruleDescr;
                            }
                        }
                    }
                }
            } while (changed);

            createLexerFile();
            createParserFile();
            createTreeFile();
            createMainFile(mainRule, input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            lexerWriter.close();
            parserWriter.close();
            treeWriter.close();
            mainWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
