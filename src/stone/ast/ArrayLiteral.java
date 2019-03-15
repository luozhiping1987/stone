package stone.ast;
import java.util.List;

public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<语法树类> list) { super(list); }
    public int size() { return 子个数(); }
}
