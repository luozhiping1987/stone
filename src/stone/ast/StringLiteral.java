package stone.ast;
import stone.词类;

public class StringLiteral extends 语法树叶类 {
    public StringLiteral(词类 t) { super(t); }
    public String value() { return 词().取文本(); }
}
