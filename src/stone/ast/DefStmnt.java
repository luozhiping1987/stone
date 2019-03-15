package stone.ast;
import java.util.List;

public class DefStmnt extends ASTList {
    public DefStmnt(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().getText(); }
    public ParameterList parameters() { return (ParameterList)子(1); }
    public BlockStmnt body() { return (BlockStmnt)子(2); } 
    public String toString() {
        return "(def " + name() + " " + parameters() + " " + body() + ")";
    }
}
