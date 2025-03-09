package lang.ast.command;

import lang.ast.Node;
import lang.ast.expr.Exp;
import lang.ast.NodeVisitor;

public class If extends Node {

    private Exp cond;
    private Node thn;
    private Node els;

    public If(int l, int c, Exp e, Node thn, Node els) {
        super(l, c);
        cond = e;
        this.thn = thn;
        this.els = els;
    }

    public Exp getCond() {
        return cond;
    }

    public Node getThn() {
        return thn;
    }

    public Node getEls() {
        return els;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }

}