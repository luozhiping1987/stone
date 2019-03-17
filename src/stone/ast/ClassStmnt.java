package stone.ast;
import java.util.List;

public class ClassStmnt extends ASTList {
  public static final String 关键字 = "类别";
  public static final String 关键字_扩展 = "扩展";

    public ClassStmnt(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().取文本(); }
    public String superClass() {
        if (子个数() < 3)
            return null;
        else
            return ((语法树叶类)子(1)).词().取文本();
    }
    public ClassBody body() { return (ClassBody)子(子个数() - 1); }
    @Override
    public String toString() {
        String parent = superClass();
        if (parent == null)
            parent = "*";
        return "(" + 关键字 + " " + name() + " " + parent + " " + body() + ")";
    }
}
