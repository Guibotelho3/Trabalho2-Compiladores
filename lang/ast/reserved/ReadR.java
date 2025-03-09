package lang.ast.reserved;

import lang.ast.Node;
import lang.ast.NodeVisitor;;

public abstract class ReadR extends Node {
    private final Node returnValue;

    public ReadR(int line, int col, Node returnValue) {
        super(line, col);
        this.returnValue = returnValue;
    }

    public Node getReturnValue() {
        return returnValue;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}