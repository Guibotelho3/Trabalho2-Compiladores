package lang.ast.visitors;


//import lang.ast.decl.*;
import java.util.Stack;

import org.omg.CORBA.Environment;

import lang.ast.NodeVisitor;
import lang.ast.command.*;
import lang.ast.expr.*;
import lang.ast.types.*;


public class Interp extends NodeVisitor{
    
    public Stack<Object> stk;
    private Environment env;

    public Interp(){
        stk = new Stack<Object>();
        this.env = new Environment();
    }

    public Object getStackTop() {
        return stk.peek(); // é isso mesmo?
    }

    @Override
    public void visit(Attrib c){
        System.out.print("Atribuindo:" + c.getVar().getName());
        c.getExp().accept(this);
        Object value = stk.pop();
        System.out.println("Valor atribuído= " + value);
        env.store(e.getVar().getName(), value);
    }

    @Override
    public void visit(Sub  e){}

    @Override
    public void visit(Plus e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop() + (Integer)stk.pop();
        stk.push(r);
    }

    @Override
    public void visit(Times e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer r = (Integer)stk.pop() * (Integer)stk.pop();
        stk.push(r);
    }

    @Override
    public void visit(Div e) {}

    @Override
    public void visit(Var e) {
        stk.push(e.getName());
    }

    @Override
    public void visit(Int e) {
        stk.push(e.getValue());
    }

    @Override
    public void visit(And e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Boolean r = (Boolean)stk.pop() && (Boolean)stk.pop();
        stk.push(r);
    }

    @Override
    public void visit(Bool e) {
        stk.push(e.getValue());
    }

    @Override
    public void visit(LValue c) {
    Object value = env.retrieve(c.getId()); // Obtém o valor armazenado para a variável
    if (value == null) {
        throw new RuntimeException("Erro: variável '" + c.getId() + "' não foi definida.");
    }
    stk.push(value); // Empilha o valor para ser usado em expressões
}

    //tyBool e tyInt? Eu tirei o IntLit e deixei só o Int
    public void visit(TyBool t) {}
    public void visit(TyInt t) {}

}
