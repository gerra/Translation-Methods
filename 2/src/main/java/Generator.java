import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Created by root on 26.03.16.
 */
public class Generator {
    private static String[] types = new String[] {"char", "int", "short", "long", "float"};
    private static String stuff = "-!@#$%^&()+=-/\\\'\"";

    private static class MyRandom extends Random {
        @Override
        public int nextInt() {
            return Math.abs(super.nextInt());
        }

        public String nextString(int length) {
            String s = "";
            for (int i = 0; i < length; i++) {
                char letter = (char) (nextInt() % 26);
                boolean lowerCase = nextBoolean();
                if (lowerCase) {
                    letter += 'a';
                } else {
                    letter += 'A';
                }
                s += letter;
            }
            return s;
        }

        public String genStuff(int length) {
            String s = "";
            for (int i = 0; i < length; i++) {
                s += stuff.charAt(nextInt() % stuff.length());
            }
            return s;
        }
    }

    private static final MyRandom random = new MyRandom();
    private final int MAX_CHAR_IN_TOKEN;

    private String description;
    private boolean simple = false;

    public Generator(boolean simple) {
        this.simple = simple;
        if (simple) {
            MAX_CHAR_IN_TOKEN = 2;
        } else {
            MAX_CHAR_IN_TOKEN = 6;
        }
    }

    private String genS() {
        String res = " ";
        int spaceCount = random.nextInt() % MAX_CHAR_IN_TOKEN + 1;
        for (int i = 1; i < spaceCount; i++) {
            res += ' ';
        }
        return res;
    }

    private String genS_() {
        boolean isSpace = random.nextBoolean();
        if (!isSpace) {
            description += "\n S\' -> ε";
            return "";
        }
        description += "\n S\' -> S";
        return genS();
    }

    private String genT() {
        int length = simple ? 1 : random.nextInt() % MAX_CHAR_IN_TOKEN + 1;
        return random.nextString(length);
    }

    private String genP() {
        String res = "*";
        int length = random.nextInt() % MAX_CHAR_IN_TOKEN + 1;
        for (int i = 1; i < length; i++) {
            if (random.nextBoolean()) {
                res += "*";
            } else {
                res += ' ';
            }
        }
        return res;
    }

    private String genK() {
        boolean firstRule = random.nextBoolean();
        if (firstRule) {
            description += "\n K -> ε";
            return "";
        }
        description += "\n K -> ,S\'W";
        return "," + genS_() + genW();
    }

    private String genW_() {
        description += "\n W\' -> S\'K";
        return genS_() + genK();
    }

    private String genV() {
        boolean firstRule = random.nextBoolean();
        if (firstRule) {
            description += "\n V -> PT";
            return genP() + genT();
        }
        description += "\n V -> T";
        return genT();
    }

    private String genW() {
        description += "\n W -> VW\'";
        return genV() + genW_();
    }

    private String genO_() {
        boolean firstRule = random.nextBoolean();
        if (firstRule) {
            description += "\n O\' -> PTW\';";
            return genP() + genT() + genW_() + ";";
        }
        description += "\n O\' -> SW;";
        return genS() + genW() + ";";
    }

    private String genO() {
        description += "\n O -> S\'TO\'";
        return genS_() + genT() + genO_();
    }

    private String genDefault() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(genS_());
        stringBuilder.append(types[random.nextInt() % types.length]);
        stringBuilder.append(genS_());
        int varCount = random.nextInt() % 5 + 1;
        for (int i = 0; i < varCount; i++) {
            if (i != 0) {
                stringBuilder.append(',')
                    .append(genS_());
            }
            int pointerLevel = random.nextInt() % 3;
            if (pointerLevel == 0 && i == 0) {
                stringBuilder.append(genS());
            }
            for (int j = 0; j < pointerLevel; j++) {
                stringBuilder.append("*")
                        .append(genS_());
            }
            stringBuilder.append(random.nextString(2));
        }
        stringBuilder.append(";");
        return stringBuilder.toString();
    }

    private String genBad() {
        int rule = random.nextInt() % 5;
        StringBuilder stringBuilder = new StringBuilder();
        if (rule == 0) {
            stringBuilder.append(genS_());
            stringBuilder.append(types[random.nextInt() % types.length]);
            stringBuilder.append(";");
        } else if (rule == 1) {
            stringBuilder.append(genS_());
            stringBuilder.append(";");
        } else if (rule == 2) {
            stringBuilder.append(genS_());
            stringBuilder.append(genP());
            stringBuilder.append(random.genStuff(3));
        } else if (rule == 3) {
            stringBuilder.append(genS_())
                    .append(types[random.nextInt() % types.length])
                    .append(genS())
                    .append(genT())
                    .append(random.genStuff(1))
                    .append(";");
        } else if (rule == 4) {
            stringBuilder.append(genS_())
                    .append(genT())
                    .append(genS())
                    .append(genT())
                    .append(",")
                    .append(genT())
                    .append(",")
                    .append(genT())
                    .append(",")
                    .append(genT());
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        Generator generator = new Generator(true);
        try (FileWriter testsWriter = new FileWriter("tests")) {
//            for (int i = 0; i < 16; i++) {
//                generator.description = "";
//                String curTest = generator.genO();
//                testsWriter.write(i + ". " + curTest + generator.description + "\n");
//                System.out.println(curTest);
//                try {
//                    Tree tree = parser.parse(new ByteArrayInputStream(curTest.getBytes()));
//                } catch (Parser.ParseException e) {
//                    e.printStackTrace();
//                    System.exit(1);
//                }
//            }

//            generator = new Generator(false);
//            for (int i = 0; i < 10; i++) {
//                generator.description = "";
//                String curTest = generator.genO();
//                System.out.println(curTest);
//                try {
//                    Tree tree = parser.parse(new ByteArrayInputStream(curTest.getBytes()));
//                } catch (Parser.ParseException e) {
//                    e.printStackTrace();
//                    System.exit(1);
//                }
//            }

            generator = new Generator(false);
            for (int i = 0; i < 5; i++) {
                generator.description = "";
                String curTest = generator.genBad();
                testsWriter.write(i+16 + ". " + curTest + "\n");

                try {
                    Tree tree = parser.parse(new ByteArrayInputStream(curTest.getBytes()));
                    System.out.println(tree);
                    throw new RuntimeException("It is the bad input, exception expected!");
                } catch (Parser.ParseException e) {
//                    e.printStackTrace();
                }
            }
        }
    }
}
