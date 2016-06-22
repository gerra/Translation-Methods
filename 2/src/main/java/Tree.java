
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 24.03.16.
 */
public class Tree {
    String node;

    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }

    public void addChild(Tree child) {
        children.add(child);
    }

    private boolean isLeaf() {
        return children == null || children.size() == 0;
    }

    public String toXml(boolean isRoot) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isRoot) {
            stringBuilder.append("<tree>\n");
            stringBuilder.append("<declarations>\n");
            stringBuilder.append("<attributeDecl name=\"name\" type=\"String\"/>\n");
            stringBuilder.append("</declarations>\n");
        }
        if (isLeaf()) {
            stringBuilder.append("<leaf>\n");
        } else {
            stringBuilder.append("<branch>\n");
        }

        stringBuilder.append("<attribute name=\"name\" value=\"")
                .append(node)
                .append("\"/>\n");

        if (children != null) {
            for (Tree child : children) {
                stringBuilder.append(child.toXml(false));
            }
        }

        if (isLeaf()) {
            stringBuilder.append("</leaf>\n");
        } else {
            stringBuilder.append("</branch>\n");
        }

        if (isRoot) {
            stringBuilder.append("</tree>\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        String children = this.children == null ? "" : ", \"children\":" + this.children.toString();
        return "{" +
                "\"node\":\"" + node + '\"' +
                children +
                '}';
    }
}
