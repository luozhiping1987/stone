package stone.ast;
import java.util.List;

public class IfStmnt extends ASTList {
    public IfStmnt(List<语法树类> c) { super(c); }
    public 语法树类 condition() { return 子(0); }
    public 语法树类 thenBlock() { return 子(1); }
    public 语法树类 elseBlock() {
        return 子个数() > 2 ? 子(2) : null;
    }
    public String toString() {
        return "(if " + condition() + " " + thenBlock()
                 + " else " + elseBlock() + ")";
    }
}
