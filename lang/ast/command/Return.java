package lang.ast.command;



import lang.ast.Node;
import lang.ast.expr.Exp;
import lang.ast.NodeVisitor;

public class ReturnCmd extends Node {

      private Exp e;

      public ReturnCmd(int line, int col, Exp e){
          super(line,col);
          this.e = e;
      }

      public Exp getExp(){ return e;}

      public void accept(NodeVisitor v){v.visit(this);}


}