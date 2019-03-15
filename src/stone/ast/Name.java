package stone.ast;
import stone.词类;

public class Name extends ASTLeaf {
    public Name(词类 t) { super(t); }
    public String name() { return token().getText(); }
}
