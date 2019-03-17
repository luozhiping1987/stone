package stone.ast;
import java.util.List;

public class Fun extends ASTList {
  public static final String 关键字 = "函数";

    public Fun(List<语法树类> c) { super(c); }
    public ParameterList parameters() { return (ParameterList)子(0); }
    public BlockStmnt body() { return (BlockStmnt)子(1); }
    @Override
    public String toString() {
        return "(" + 关键字 + " " + parameters() + " " + body() + ")";
    }
}
