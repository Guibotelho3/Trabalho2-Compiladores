package lang.ast.expr;

import lang.ast.NodeVisitor;

public abstract class Dot extends BinOp {
    public Dot(int line, int col, Exp el, Exp er) {
        super(line, col, el, er);
    }

    public String toString() {
        return ".";
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}