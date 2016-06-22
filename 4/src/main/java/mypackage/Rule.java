package mypackage;

import java.util.*;

public class Rule {

    static abstract class RulePart {
        // RULE_REF, TOKEN_REF, QUOTED_STR, ASSIGNMENT

        String getS1() {
            return "";
        }

        String getS2() {
            return "";
        }
    }

    static class RulePartStr extends RulePart {
        String string;

        public RulePartStr(String string) {
            this.string = string;
        }
    }

    static class RulePartAssignR extends RulePart {
        String name;
        Rule rule;

        public RulePartAssignR(String name, Rule rule) {
            this.name = name;
            this.rule = rule;
        }

        @Override
        String getS1() {
            return name;
        }

        @Override
        String getS2() {
            return rule.name;
        }
    }

    static class RulePartAssignT extends RulePart {
        String name;
        Token token;

        public RulePartAssignT(String name, Token token) {
            this.name = name;
            this.token = token;
        }

        @Override
        String getS1() {
            return name;
        }

        @Override
        String getS2() {
            return token.name;
        }
    }

//    static class Assignment {}

    static class RuleDescr {
        Rule parentRule;
        List<RulePart> ruleParts = new ArrayList<>();
        String aciton = "";

        void addRulePart(RulePart part) {
            ruleParts.add(part);
        }

        void setAction(String action) {
            this.aciton = action;
        }

        public void setParentRule(Rule parentRule) {
            this.parentRule = parentRule;
        }
    }

    static class Var {
        String type;
        String name;

        public Var(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    String name;
    RuleDescr epsRuleDescr = null;
    List<RuleDescr> ruleDescrs = new ArrayList<>();
    List<Var> takeList = new ArrayList<>();
    List<Var> returnList = new ArrayList<>();

    private static Map<String, List<Rule>> rulePool = new HashMap<>();

    public Rule(String name) {
        this.name = name;
        List<Rule> rules = rulePool.get(name);
        if (rules == null) {
            rules = new ArrayList<>();
            rulePool.put(name, rules);
        }
        rules.add(this);
    }

    public RuleDescr getEpsRuleDescr() {
        List<Rule> rules = rulePool.get(name);
        for (Rule rule : rules) {
            if (rule.epsRuleDescr != null) {
                return rule.epsRuleDescr;
            }
        }
        return null;
    }

    void addRuleDescr(RuleDescr ruleDescr) {
        ruleDescr.setParentRule(this);
        ruleDescrs.add(ruleDescr);
    }

    void addTake(Var var) {
        takeList.add(var);
    }

    void addReturn(Var var) {
        returnList.add(var);
    }
}
