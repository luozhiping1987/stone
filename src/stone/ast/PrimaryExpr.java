package stone.ast;
import java.util.List;

public class PrimaryExpr extends ASTList {
    public PrimaryExpr(List<语法树类> c) { super(c); }
    public static 语法树类 create(List<语法树类> c) {
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }
}
