package stone.ast;
import java.util.List;

public class VarStmnt extends ASTList {
    public VarStmnt(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().getText(); }
    public TypeTag type() { return (TypeTag)子(1); }
    public 语法树类 initializer() { return 子(2); }
    public String toString() {
        return "(var " + name() + " " + type() + " " + initializer() + ")";
    }
}
