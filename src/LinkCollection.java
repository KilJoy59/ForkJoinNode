import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class LinkCollection extends RecursiveTask<String> {
  private final Node node;

  LinkCollection(Node node) {
    this.node = node;
  }

  @Override
  protected String compute() {
    StringBuilder buffer = new StringBuilder(node.getLink());
    System.out.println(buffer.toString());
    List<LinkCollection> subTasks = new LinkedList<>();

    try {
      List<Node> getChildren = node.getChildren();
      if (getChildren != null) {
        for (Node child : getChildren) {
          LinkCollection task = new LinkCollection(child);
          task.fork();
          subTasks.add(task);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    for (LinkCollection task : subTasks) {
      String res = task.join();
      String tab = node.getLevel();
      buffer.append(tab).append(res);
    }
    return buffer.toString();
  }
}