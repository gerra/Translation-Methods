package test;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Need test file");
            System.exit(1);
        }
        Parser parser = new Parser(new FileInputStream(args[0]));
        parser.parsedecls();
    }
}
