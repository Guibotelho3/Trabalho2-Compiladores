package lang.ast.decl;

import java.util.List;

import lang.ast.Node;
import lang.ast.NodeVisitor;
import lang.ast.command.Cmd;

public class Block extends Node {
    private List<Cmd> commands;

    public Block(int line, int col, List<Cmd> commands) {
        super(line, col);
        this.commands = commands;
    }

    public List<Cmd> getCommands() {
        return commands;
    }

    public void accept(NodeVisitor v) {
        v.visit(this);
    }
}
