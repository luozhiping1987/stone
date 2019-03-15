package stone.ast;
import stone.词类;

public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(词类 t) { super(t); }
    public int value() { return token().getNumber(); }
}
