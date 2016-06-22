package misc;

public class Utils {
    public static String genTab(int cnt) {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < cnt; i++) {
            s.append("  ");
        }
        return s.toString();
    }
    public static String nn() {
        return "\n";
    }
}
