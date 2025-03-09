package lang.ast.reserved;

import lang.ast.Node;
import lang.ast.NodeVisitor;;

public class Iterate extends Node {
    private final Node condition;
    private final Node body;

    public Iterate(int line, int col, Node condition, Node body) {
        super(line, col);
        this.condition = condition;
        this.body = body;
    }

    public LNode getCondition() {
        return condition;
    }

    public LNode getBody() {
        return body;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}