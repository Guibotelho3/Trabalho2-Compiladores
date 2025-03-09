package lang.ast.delimiters;

import lang.ast.NodeVisitor;

public class LBrace extends Delimiter {
    public LBrace(int line, int col) {
        super(line, col);
    }

    public String toString() {
        return "{";
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}