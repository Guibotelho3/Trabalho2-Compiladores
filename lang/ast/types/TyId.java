package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyId extends LType {
    public TyId(int line, int col, String typeName) {
        super(line, col);
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}