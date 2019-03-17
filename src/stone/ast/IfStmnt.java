package stone.ast;
import java.util.List;

public class IfStmnt extends ASTList {
  public static final String 关键字_如果 = "如果";
  public static final String 关键字_否则 = "否则";

    public IfStmnt(List<语法树类> c) { super(c); }
    public 语法树类 condition() { return 子(0); }
    public 语法树类 thenBlock() { return 子(1); }
    public 语法树类 elseBlock() {
        return 子个数() > 2 ? 子(2) : null;
    }
    @Override
    public String toString() {
        return "(" + 关键字_如果 + " " + condition() + " " + thenBlock()
                 + " " + 关键字_否则 + " " + elseBlock() + ")";
    }
}
