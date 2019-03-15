package stone.ast;
import java.util.List;

public class ArrayRef extends Postfix {
    public ArrayRef(List<语法树类> c) { super(c); }
    public 语法树类 index() { return 子(0); }
    public String toString() { return "[" + index() + "]"; }
}
