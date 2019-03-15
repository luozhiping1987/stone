package stone.ast;
import stone.词类;

public class NumberLiteral extends 语法树叶类 {
    public NumberLiteral(词类 t) { super(t); }
    public int value() { return 词().getNumber(); }
}
