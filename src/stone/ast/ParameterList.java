package stone.ast;
import java.util.List;

public class ParameterList extends ASTList {
    public ParameterList(List<语法树类> c) { super(c); }
    public String name(int i) { return ((语法树叶类)子(i)).词().getText(); }
    public int size() { return 子个数(); }
}
