package stone.ast;
import java.util.List;

public class TypeTag extends ASTList {
    public static final String UNDEF = "<Undef>";
    public TypeTag(List<语法树类> c) { super(c); }
    public String type() {
        if (子个数() > 0)
            return ((语法树叶类)子(0)).词().取文本();
        else
            return UNDEF;
    }
    public String toString() { return ":" + type(); }
}
