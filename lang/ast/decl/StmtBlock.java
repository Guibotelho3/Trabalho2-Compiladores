package lang.ast.decl;

import java.util.ArrayList;
import java.util.List;

import lang.ast.Node;
import lang.ast.NodeVisitor;

public class StmtBlock extends Node {
    private List<Node> body;

    public StmtBlock(int l, int c, List<? extends Node> body) {
        super(l, c);
        this.body = new ArrayList<>(body);
    }

    public List<Node> getBody() {
        return body;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}