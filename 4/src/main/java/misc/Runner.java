package misc;

import homework3.Parser;

import java.io.*;

public class Runner {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("grammar name & path to tests were expected");
            return;
        }
        try {
            if (args[0].startsWith("homework3")) {
                FileInputStream stream = new FileInputStream(new File(args[1]));
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String s;
                int i = 1;
                while((s = reader.readLine()) != null) {
                    System.out.println("========== Test #"+i+" ==========");
                    homework3.Parser parser = new homework3.Parser(new ByteArrayInputStream(s.getBytes()));
                    System.out.println(parser.parseprog("{","}").s+"\n");
                    i++;
                }
            } else if (args[0].startsWith("homework2")) {
                FileInputStream stream = new FileInputStream(new File(args[1]));
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String s;
                int i = 1;
                while((s = reader.readLine()) != null) {
                    System.out.println("========== Test #"+i+" ==========");
                    homework2.Parser parser = new homework2.Parser(new ByteArrayInputStream(s.getBytes()));
                    try {
                        parser.parseprog();
                    } catch (IOException e) {
                        System.out.println("parse error");
                    }
                    i++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
