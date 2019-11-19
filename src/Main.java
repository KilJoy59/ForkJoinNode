import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final String URL = "https://skillbox.ru/";

    public static void main(String[] args) {
        Node root = getRootNode();
        String result = new ForkJoinPool().invoke(new LinkCollection(root));

        try {
            FileWriter fileWriter = new FileWriter("siteMap.txt");
            fileWriter.write(result);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static Node getRootNode() {
        return new LinkThief(URL, new TreeSet<>(Comparator.naturalOrder()), "");
    }
}