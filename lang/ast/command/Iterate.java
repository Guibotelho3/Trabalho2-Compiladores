package lang.ast.command;


import lang.ast.Node;
import lang.ast.expr.Exp;
import lang.ast.NodeVisitor;

public class IterateCmd extends Node{

   private Exp cond;
   private Node body;

   public IterateCmd(int l, int c, Exp e, Node body){
       super(l,c);
       cond = e;
       this.body = body;
   }

   public Exp getCond(){return cond;}
   public Node getBody(){return body;}

   public void accept(NodeVisitor v){v.visit(this);}



}