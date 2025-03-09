package lang.ast.command;

import lang.ast.NodeVisitor;
import lang.ast.expr.Exp;
import lang.ast.expr.Var;

public class Assign extends Cmd {
    private Var var; // Lado esquerdo da atribuição (variável)
    private Exp exp; // Lado direito da atribuição (expressão)

    public Assign(int line, int column, Var var, Exp exp) {
        super(line, column);
        this.var = var;
        this.exp = exp;
    }

    public Var getVar() {
        return var;
    }

    public Exp getExp() {
        return exp;
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}