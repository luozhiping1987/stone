package stone.ast;
import java.util.List;

public class Fun extends ASTList {
    public Fun(List<语法树类> c) { super(c); }
    public ParameterList parameters() { return (ParameterList)子(0); }
    public BlockStmnt body() { return (BlockStmnt)子(1); }
    public String toString() {
        return "(fun " + parameters() + " " + body() + ")";
    }
}
