package lang.ast.expr;

import lang.ast.Node;
import lang.ast.NodeVisitor;

public class LValue extends Node {
    private String id;

    public LValue(int line, int column, String id) {
        super(line, column);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public void accept(NodeVisitor v) {
        v.visit(this);
    }
    
    @Override
    public String toString() {
        return "LValue(" + id + ")";
    }
}