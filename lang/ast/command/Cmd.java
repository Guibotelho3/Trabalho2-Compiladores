package lang.ast.command;

import lang.ast.Node;

public abstract class Cmd extends Node {
    public Cmd(int line, int col) {
        super(line, col);
    }
}
