package lang.ast.reserved;

import lang.ast.Node;
import lang.ast.NodeVisitor;;

public class PrintR extends Node {
    private Node e;

    public PrintR(int line, int col, Node e) {
        super(line, col);
        this.e = e;
    }

    public Node getRes() {
        return e;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}