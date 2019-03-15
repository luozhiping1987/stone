package stone.ast;
import stone.词类;

public class StringLiteral extends ASTLeaf {
    public StringLiteral(词类 t) { super(t); }
    public String value() { return token().getText(); }
}
