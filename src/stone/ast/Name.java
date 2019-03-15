package stone.ast;
import stone.词类;

public class Name extends 语法树叶类 {
    public Name(词类 t) { super(t); }
    public String name() { return 词().getText(); }
}
