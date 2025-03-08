package lang.ast.command;
import lang.ast.NodeVisitor;
import lang.ast.command.Cmd;
import lang.ast.expr.Exp;
import lang.ast.expr.Var;

public class Attrib extends Cmd{
    private Var var;
    private Exp exp;

    public Attrib(int ileft, int iright, Var var, Exp exp) {
        super(ileft, iright);
        this.var = var;
        this.exp = exp;
    }

    public Var getVar() {
        return var;
    }

    public Exp getExp() {
        return exp;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
