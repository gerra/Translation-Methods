import java.io.*;
import java.text.ParseException;

/**
 * Created by root on 23.03.16.
 */

public class Main {
    public static void main(String[] args) throws IOException {
        try (InputStream is = (args.length == 1 ? new ByteArrayInputStream(args[0].getBytes()) : new FileInputStream("test"))) {
            Parser parser = new Parser();
            Tree tree = parser.parse(is);

            FileWriter fileWriter = new FileWriter("test.res");
            String treeAsXml = tree.toXml(true);
            fileWriter.write(treeAsXml);
            fileWriter.close();

            System.out.println(tree);
            TreeView.main(new String[] {"test.res", "name"});
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
