package logic.pathfinding;

import com.badlogic.gdx.ai.pfa.indexed.DefaultIndexedGraph;

public class NodeGraph extends DefaultIndexedGraph<PathNode> {

    public NodeGraph(int size) {
        super(size);
    }

    public void addNode(PathNode node) {
        nodes.add(node);
    }

    public PathNode getNode(int index) {
        return nodes.get(index);
    }
}
