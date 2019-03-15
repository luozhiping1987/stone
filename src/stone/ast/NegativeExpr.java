package stone.ast;
import java.util.List;

public class NegativeExpr extends ASTList {
    public NegativeExpr(List<语法树类> c) { super(c); }
    public 语法树类 operand() { return 子(0); }
    public String toString() {
        return "-" + operand();
    }
}
