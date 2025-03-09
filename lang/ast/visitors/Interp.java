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


// Command

    
    @Override
    public void visit(Assign c) {
        System.out.print("Atribuindo: " + c.getVar().getName());
        
        // Avalia a expressão à direita (o valor a ser atribuído)
        c.getExp().accept(this);
        Object value = stk.pop();  // O valor resultante da expressão à direita
        
        // Atualiza a variável à esquerda com o valor calculado
        env.store(c.getVar().getName(), value);
        System.out.println("Valor atribuído= " + value);
    }


    @Override
    public void visit(Atbr c) {
    System.out.print("Atribuindo (Atbr): " + c.getLhs().toString());

    // Avalia a expressão à direita (o valor a ser atribuído)
    c.getExp().accept(this);
    Object value = stk.pop();  // O valor resultante da expressão à direita
    
    // Avalia o lado esquerdo (onde o valor será armazenado)
    c.getLhs().accept(this);
    Object lhsValue = stk.pop();  // Lado esquerdo da atribuição (onde o valor será armazenado)
    
    // Atualiza o valor no ambiente para a variável no lado esquerdo
    env.store(lhsValue.toString(), value);
    System.out.println("Valor atribuído= " + value);
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
    public void visit(Cmd c) {
    // Cmd é uma classe base, então nada específico é feito aqui
    // Isso garante que a visitação ao Cmd seja tratada corretamente
    // Em implementações concretas de Cmd, o comportamento específico seria definido
}
    @Override
    public void visit(If c) {
    // Avalia a condição (condição do if)
    c.getCond().accept(this);
    Boolean condition = (Boolean) stk.pop();  // A condição é esperada ser do tipo Boolean

    // Verifica a condição e executa o bloco correspondente
    if (condition) {
        // Se a condição for verdadeira, executa o bloco 'thn'
        c.getThn().accept(this);
    } else {
        // Caso contrário, executa o bloco 'els'
        if (c.getEls() != null) {
            c.getEls().accept(this);
        }
    }
}
    @Override
    public void visit(IfElse c) {
    // Avalia a condição (condição do if-else)
    c.getCondition().accept(this);
    Boolean condition = (Boolean) stk.pop();  // A condição é esperada ser do tipo Boolean

    // Verifica a condição e executa o bloco correspondente
    if (condition) {
        // Se a condição for verdadeira, executa o bloco 'thenStmtBlock'
        c.getThenStmtBlock().accept(this);
    } else {
        // Caso contrário, executa o bloco 'elseStmtBlock'
        c.getElseStmtBlock().accept(this);
    }
}

    @Override
    public void visit(Iterate c) {
    // Enquanto a condição for verdadeira, o corpo será repetidamente executado
    while (true) {
        // Avalia a condição da iteração
        c.getCond().accept(this);
        Boolean condition = (Boolean) stk.pop();  // A condição é esperada ser do tipo Boolean

        // Se a condição for falsa, a iteração é interrompida
        if (!condition) {
            break;
        }

        // Executa o corpo da iteração
        c.getBody().accept(this);
    }
}
    @Override
    public void visit(Print c) {
    // Avalia a expressão que deve ser impressa
    c.getExp().accept(this);
    // O valor da expressão será empilhado no topo da pilha
    Object value = stk.pop();

    // Imprime o valor resultante
    System.out.println(value);
}

    @Override
    public void visit(Read c) {
    // Solicita ao usuário que insira um valor (dependendo do tipo, pode ser inteiro, float, etc.)
    System.out.print("Digite um valor para " + c.getLValue() + ": ");
    
    // Leitura do valor da entrada
    java.util.Scanner scanner = new java.util.Scanner(System.in);
    
    // Dependendo do tipo da variável a ser lida (determinada por LValue), lê o valor correto
    Object value = null;
    if (c.getLValue() instanceof LValue) {
        // Lê um valor inteiro, mas você pode adicionar suporte para outros tipos, como Float, Boolean, etc.
        value = scanner.nextInt();
    }
    
    @Override
    public void visit(Return c) {
    // Avalia a expressão de retorno
    c.getExp().accept(this);
    
    // O valor da expressão será empilhado na pilha
    Object value = stk.pop();
    
    // Neste ponto, o valor de retorno pode ser processado ou armazenado de acordo com a necessidade
    // Por exemplo, se o interpretador tiver uma função de retorno, você pode fazer algo como:
    System.out.println("Valor de retorno: " + value);
    
    // Se houver um mecanismo para retornar valores em uma função, o valor poderia ser armazenado e utilizado
    // Aqui estamos apenas imprimindo o valor, mas você pode armazenar no ambiente ou na pilha dependendo da implementação
    stk.push(value); // Aqui, o valor de retorno é empilhado para continuar o fluxo de execução
}





     //Decl

     @Override
    public void visit(ArrayAccess aa) {
    // Avalia a base do array
    aa.getBase().accept(this);
    Object array = stk.pop();
    
    // Avalia o índice do array
    aa.getIndex().accept(this);
    Object indexObj = stk.pop();
    
    if (array instanceof Object[] && indexObj instanceof Integer) {
        // Acessa o elemento do array usando o índice
        Object[] arr = (Object[]) array;
        int index = (Integer) indexObj;
        
        if (index < 0 || index >= arr.length) {
            throw new IndexOutOfBoundsException("Índice fora dos limites do array");
        }
        
        // Empilha o valor do elemento no array
        stk.push(arr[index]);
    } else {
        throw new RuntimeException("Tipo incompatível para acesso ao array ou índice");
    }
}


    @Override
    public void visit(Bind bind) {
    // Avalia o valor da variável (do tipo TyChar)
    bind.getVar().accept(this);
    Object value = stk.pop();
    
    // Verifica se o valor é do tipo esperado (Character)
    if (value instanceof Character) {
        // Se for, armazena na variável
        env.store(bind.getVar().getName(), value);
        System.out.println("Bind: Variável " + bind.getVar().getName() + " associada ao valor " + value);
    } else {
        throw new RuntimeException("Erro: tipo incompatível, esperado um 'Char' para a variável " + bind.getVar().getName());
    }
}

        
@Override
public void visit(Block block) {
    // Itera sobre os comandos do bloco e os avalia um a um
    for (Cmd cmd : block.getCommands()) {
        cmd.accept(this);  // Avalia cada comando
    }
}

@Override
public void visit(Decl decl) {
    // Obtém o tipo da variável
    LType type = decl.getFieldType();
    Object defaultValue = null; // Valor padrão para a variável

    // Define o valor inicial dependendo do tipo
    if (type instanceof TyInt) {
        defaultValue = 0; // Valor padrão para inteiros
    } else if (type instanceof TyBool) {
        defaultValue = false; // Valor padrão para booleanos
    } else if (type instanceof TyChar) {
        defaultValue = '\0'; // Valor padrão para caracteres
    }

    // Armazena a variável no ambiente com o valor padrão
    env.store(decl.getFieldName(), defaultValue);
}

@Override
public void visit(FieldAccess fieldAccess) {
    // Primeiramente, acessa o valor do "base" (que pode ser um objeto ou uma estrutura)
    fieldAccess.getBase().accept(this);  // Visita o base (ex: objeto ou estrutura)

    // Agora, a variável "field" será o nome do campo a ser acessado
    Object baseValue = stk.pop();  // O valor do base é retirado da pilha (deve ser um objeto ou estrutura)

    // Aqui, precisamos acessar o campo específico dentro da estrutura.
    // Supondo que "baseValue" seja uma instância de um objeto que armazena campos (por exemplo, um Map ou uma classe)
    // O código para acessar o campo depende de como você modela as estruturas no seu ambiente.
    Object fieldValue = getFieldFromBase(baseValue, fieldAccess.getField());

    stk.push(fieldValue);  // Empilha o valor do campo
}

private Object getFieldFromBase(Object baseValue, String field) {
    // Aqui você deve acessar o campo do objeto/estrutura. Exemplo para um Map:
    if (baseValue instanceof Map) {
        return ((Map<String, Object>) baseValue).get(field);  // Acessa o campo em um Map
    } else if (baseValue instanceof SomeClass) {
        // Se baseValue for uma instância de uma classe com campos específicos, você pode acessar diretamente
        return ((SomeClass) baseValue).getField(field);  // Exemplo de método fictício
    }
    throw new RuntimeException("Campo não encontrado para a base: " + baseValue);
}

@Override
public void visit(FunDef funDef) {
    // Armazenando a função no ambiente
    env.storeFunction(funDef.getFname(), funDef);
    System.out.println("Função definida: " + funDef.getFname());
}

@Override
public void visit(Param param) {

    env.store(param.getId(), null); // Adiciona o parâmetro com valor inicial "null".
    System.out.println("Parâmetro: " + param.getId() + " de tipo " + param.getType());
}
@Override
public void visit(StmtBlock stmtBlock) {
    // Cria um novo ambiente para este bloco de comandos, garantindo escopo adequado
    Environment localEnv = new Environment();

    // Empilha o novo ambiente para tratar a execução dentro deste escopo
    this.env = localEnv;

    // Executa cada comando dentro do bloco de declarações
    for (Node cmd : stmtBlock.getBody()) {
        cmd.accept(this); // Chama o método accept, que vai acionar a execução do comando
    }

    // Após a execução, retorna ao ambiente anterior
    this.env = localEnv.getOuterEnvironment();
}





//Delimiters

@Override
public void visit(Comma del) {}

@Override
public void visit(Delimiter del) {}

@Override
public void visit(LBrace del) {}

@Override
public void visit(LBracket del) {}

@Override
public void visit(LParen del) {}

@Override
public void visit(RBrace del) {}

@Override
public void visit(RBracket del) {}

@Override
public void visit(RParen del) {}

@Override
public void visit(Semicolon del) {}






    //Expr

    @Override
    public void visit(Sub e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer right = (Integer) stk.pop();
        Integer left = (Integer) stk.pop();
        stk.push(left - right);
    }

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
    public void visit(Div e) {
        e.getLeft().accept(this);
        e.getRight().accept(this);
        Integer right = (Integer) stk.pop();
        if (right == 0) {
            throw new RuntimeException("Erro: divisão por zero.");
        }
        Integer left = (Integer) stk.pop();
        stk.push(left / right);
    }

    @Override
public void visit(Var e) {
    Object value = env.retrieve(e.getName());
    if (value == null) {
        throw new RuntimeException("Erro: variável '" + e.getName() + "' não foi definida.");
    }
    stk.push(value);
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

@Override
public void visit(Mod e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Integer right = (Integer) stk.pop();
    Integer left = (Integer) stk.pop();
    stk.push(left % right);
}

@Override
public void visit(Equal e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Object right = stk.pop();
    Object left = stk.pop();
    stk.push(left.equals(right));
}

@Override
public void visit(Different e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Object right = stk.pop();
    Object left = stk.pop();
    stk.push(!left.equals(right));
}


@Override
public void visit(Less e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Integer right = (Integer) stk.pop();
    Integer left = (Integer) stk.pop();
    stk.push(left < right);
}

@Override
public void visit(Greater e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
    Integer right = (Integer) stk.pop();
    Integer left = (Integer) stk.pop();
    stk.push(left > right);
}

@Override
public void visit(Not e) {
    e.getExp().accept(this);
    Boolean value = (Boolean) stk.pop();
    stk.push(!value);
}

@Override
public void visit(BinOp e) {
    e.getLeft().accept(this);
    e.getRight().accept(this);
}

@Override
public void visit(CharLit e) {
    stk.push(e.getValue());
}
@Override
public void visit(Exp e) {
    e.accept(this);
}

@Override
public void visit(Float e) {
    stk.push(e.getValue());
}

@Override
public void visit(Null e) {
    stk.push(null);
}






//Types

@Override
public void visit(LType t) {
    stk.push(t);
}

@Override
public void visit(TyBool t) {
    stk.push("boolean");
}

@Override
public void visit(TyChar t) {
    stk.push("char");
}

@Override
public void visit(TyId t) {
    stk.push(t.getId());
}

@Override
public void visit(TyInt t) {
    stk.push("int");
}

@Override
public void visit(TyNull t) {
    stk.push("null");
}

@Override
public void visit(TyFloat t) {
    stk.push("float");
}




    //tyBool e tyInt? Eu tirei o IntLit e deixei só o Int
    


// Reserved


@Override
public void visit(Data e) {
    String typeName = e.getTypeName();  // Obter o nome do tipo
    List<Decl> fields = e.getFields();  // Obter a lista de campos

    // Criar um novo tipo e adicionar no ambiente
    Environment env = getEnvironment();  // Supondo que você tenha um ambiente
    env.createDataType(typeName, fields);  // Adicionar a definição do tipo de dado no ambiente

    // Caso você queira imprimir ou retornar algo ao interpretador:
    System.out.println("Tipo de dado " + typeName + " criado com os campos: " + fields);
}

@Override
public void visit(IterateR e) {
    Exp condition = e.getCond();
    Node body = e.getBody();

    while (condition.evaluate()) {  // Exemplo de avaliação da condição
        body.accept(this);  // Executar o corpo do loop
    }
}
@Override
public void visit(PrintR e) {
    Exp exp = e.getExp();
    Object result = exp.evaluate();  // Avaliar a expressão dentro do print
    System.out.println(result);  // Imprimir o valor
}

@Override
public void visit(ReadR e) {
    LValue lvalue = e.getLValue();
    Object value = readInput();  // Método para ler a entrada do usuário
    lvalue.setValue(value);  // Atribuir o valor lido ao lvalue
}
@Override
public void visit(Return e) {
    Exp exp = e.getExp();
    Object returnValue = exp.evaluate();
    return returnValue;  // Retornar o valor da expressão
}
@Override
public void visit(Res e) {
    Object result = e.getResult(); // Recupera o resultado armazenado no comando Res
    
    // Aqui você pode fazer algo com o resultado, por exemplo:
    System.out.println("Resultado: " + result);  // Imprime o resultado

    // Ou você pode armazená-lo em algum lugar, ou usá-lo em um contexto mais amplo
    // Como talvez adicionar ao ambiente ou à pilha do interpretador, dependendo de como sua arquitetura é projetada
    stk.push(result);  // Exemplo de empilhar o resultado na pilha
}









}
