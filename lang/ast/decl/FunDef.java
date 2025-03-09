package lang.ast.decl;

import lang.ast.Node;
import lang.ast.types.TyChar;
import java.util.ArrayList;
import lang.ast.NodeVisitor;

public class FunDef extends Node{

   private String fname;
   private ArrayList<Bind> params;
   private TyChar ret;
   private Node body;

   public FunDef(int l, int c, ArrayList<Bind> params, TyChar ret, Node body){
       super(l,c);
       this.params = params;
       this.ret = ret;
       this.body = body;
   }

   public String getFname(){return fname;}
   public ArrayList<Bind>  getParams(){return params;}
   public Node getBody(){return body;}
   public TyChar getRet(){return ret;}

   public void accept(NodeVisitor v){v.visit(this);}

}