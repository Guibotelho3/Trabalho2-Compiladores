package lang.ast.reserved;

import java.util.List;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.decl.Decl;

public class Data extends Node {
    private final String typeName;
    private final List<Decl> fields;

    // Construtor com verificação de nulos para a lista de campos
    public Data(int line, int col, String typeName, List<Decl> fields) {
        super(line, col);
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException("O nome do tipo não pode ser nulo ou vazio.");
        }
        this.typeName = typeName;
        this.fields = (fields == null) ? List.of() : fields; // Garantir que a lista nunca seja nula
    }

    // Getter para o nome do tipo
    public String getTypeName() {
        return typeName;
    }

    // Getter para a lista de campos
    public List<Decl> getFields() {
        return fields;
    }

    // Implementação do método accept para o NodeVisitor
    public void accept(NodeVisitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "Data(" + typeName + ", " + fields + ")";
    }
}
