package lang.ast.environment;

import java.util.HashMap;
import java.util.Map;

public class Env {

    private HashMap<String, Object> m;

    public Env() {
        m = new HashMap<String, Object>(100);  // Inicializa com uma capacidade inicial de 100
    }

    // Armazena uma variável com seu valor associado
    public void store(String vname, Object value) {
        m.put(vname, value);
    }

    // Recupera o valor de uma variável, se ela não existir lança uma exceção
    public Object read(String vname) {
        Object value = m.get(vname);
        if (value == null) {
            throw new RuntimeException("Unknown variable: " + vname);
        }
        return value;
    }

    // Método de repetição para formatar a exibição da tabela
    private String repeatStr(int n, String c) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(c);
        }
        return s.toString();
    }

    // Exibe todas as variáveis e seus valores no ambiente
    public void dumpTable() {
        String title = "Variable";
        System.out.println(repeatStr(6 - title.length() / 2, " ") + title + repeatStr(6 - title.length() / 2, " ") + "|  Value");
        System.out.println(repeatStr(22, "-"));
        for (Map.Entry<String, Object> e : m.entrySet()) {
            System.out.println(e.getKey() + repeatStr(12 - e.getKey().length(), " ") + "| " + e.getValue());
            System.out.println(repeatStr(22, "-"));
        }
    }

    // Método para verificar se uma variável já foi definida
    public boolean contains(String vname) {
        return m.containsKey(vname);
    }
}
