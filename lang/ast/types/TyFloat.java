package lang.ast.types;

import lang.ast.NodeVisitor;

public class TyFloat extends Type {
    public TyFloat(int line, int col) {
        super(line, col, "Float");
    }

    public void accept(LVisitor v) {
        v.visit(this);
    }
}