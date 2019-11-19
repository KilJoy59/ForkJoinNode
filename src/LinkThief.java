import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;

public class LinkThief implements Node {
    private String link;
    private TreeSet<String> children;
    private String level;

    LinkThief(String link, TreeSet<String> children, String level) {
        this.link = link;
        this.children = children;
        this.level = level;
    }

    @Override
    public List<Node> getChildren() throws IOException {
        String line = link;
        Document document = Jsoup.connect(link).ignoreContentType(true).maxBodySize(0).get();
        Elements elements = document.select("a[href]");
          Set<String> treeLinks = new TreeSet<>();
        for (Element element : elements) {
          String newLink = element.absUrl("href");
          if (!children.contains(newLink)
                  && newLink.contains(line)
                  && !newLink.endsWith(".pdf")
                  && !newLink.contains("#")
                  && !newLink.contains("?"))
          {
            treeLinks.add(newLink);
            children.add(newLink);
          }
        }

        if (elements.size() == 0) {
            return null;
        }

      List<Node> nodeCollection = new ArrayList<>();
      for (String link : treeLinks) {
        nodeCollection.add(new LinkThief(link,children, level + "\t"));
      }
        return nodeCollection;
    }

    @Override
    public String getLink() {
        return link + "\n";
    }

  @Override
  public String getLevel() {
    return level;
  }
}