package mypackage;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 02.06.16.
 */
public class Utils {
    public static int indexOfLastMatch(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        for (int i = input.length(); i > 0; i--) {
            Matcher region = matcher.region(0, i);
            if (region.matches() || region.hitEnd()) {
                return i;
            }
        }

        return 0;
    }

    static String wildcardToRegex(String wildcard){
        StringBuffer s = new StringBuffer(wildcard.length());
        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch(c) {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                // escape special regexp-characters
                case '(': case ')': case '[': case ']': case '$':
                case '^': case '.': case '{': case '}': case '|':
                case '\\':
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
        }
        return(s.toString());
    }

    public static List<Character> getFirstCharsOfRegex(String regex) {
        Pattern pattern = Pattern.compile(regex);
        List<Character> res = new ArrayList<>();
        for (char c = 0; c < 256; c++) {
            if (indexOfLastMatch(pattern, "" + c) == 1) {
                res.add(c);
            }
        }
        return res;
    }

    public static String getFirstCharsOfRegexAsString(String regex) {
        List<Character> characters = getFirstCharsOfRegex(regex);
        StringBuilder resBuilder = new StringBuilder();
        for (char c : characters) {
            resBuilder
                    .append('\'')
                    .append(c)
                    .append('\'')
                    .append(',');
        }
        resBuilder.deleteCharAt(resBuilder.length() - 1);
        return resBuilder.toString();
    }
}
