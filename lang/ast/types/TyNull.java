package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyNull extends LType {
    public TyNull(int line, int col) {
        super(line, col);
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}