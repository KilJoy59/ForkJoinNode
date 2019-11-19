import java.util.List;

public interface Node {
  List<Node> getChildren() throws Exception;
  String getLink();
  String getLevel();
}