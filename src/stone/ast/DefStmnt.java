package stone.ast;
import java.util.List;

public class DefStmnt extends ASTList {
  public static final String 关键字 = "定义";

    public DefStmnt(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().取文本(); }
    public ParameterList parameters() { return (ParameterList)子(1); }
    public BlockStmnt body() { return (BlockStmnt)子(2); }
    @Override
    public String toString() {
        return "(" + 关键字 + " " + name() + " " + parameters() + " " + body() + ")";
    }
}
