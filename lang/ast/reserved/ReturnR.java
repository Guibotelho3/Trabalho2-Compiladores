package lang.ast.reserved;

import lang.ast.Node;
import lang.ast.NodeVisitor;;

public class ReturnR extends Node {
    private Res e;

    public ReturnR(int line, int col, Res e) {
        super(line, col);
        this.e = e;
    }

    public Res getRes() {
        return e;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}