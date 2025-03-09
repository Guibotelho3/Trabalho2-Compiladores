package lang.ast.delimiters;

import lang.ast.NodeVisitor;

public class RParen extends Delimiter {
    public RParen(int line, int col) {
        super(line, col);
    }

    public String toString() {
        return ")";
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }

}