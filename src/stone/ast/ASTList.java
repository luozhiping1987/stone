package stone.ast;
import java.util.List;
import java.util.Iterator;

public class ASTList extends 语法树类 {
    protected List<语法树类> children;
    public ASTList(List<语法树类> list) { children = list; }
    public 语法树类 子(int i) { return children.get(i); }
    public int 子个数() { return children.size(); }
    public Iterator<语法树类> children() { return children.iterator(); }
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('(');
        String sep = "";
        for (语法树类 t: children) {
            builder.append(sep);
            sep = " ";
            builder.append(t.toString());
        }
        return builder.append(')').toString();
    }
    public String location() {
        for (语法树类 t: children) {
            String s = t.location();
            if (s != null)
                return s;
        }
        return null;
    }
}
