package lang.ast.expr;

import lang.ast.NodeVisitor;

public class Int extends Exp{

      private int value;
      public Int(int line, int col, int value){
           super(line,col);
           this.value = value;
      }

      public int getValue(){ return value;}



      public void accept(NodeVisitor v){v.visit(this);}


}
