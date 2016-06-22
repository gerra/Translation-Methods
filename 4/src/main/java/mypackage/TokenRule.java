package mypackage;

/**
 * Created by root on 02.06.16.
 */
public class TokenRule {
    String name;
    String regex;

    public TokenRule(String name) {
        this.name = name;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
