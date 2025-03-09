package lang.ast.delimiters;

import lang.ast.Node;

public abstract class Delimiter extends Node {
    public Delimiter(int line, int col) {
        super(line, col);
    }
}