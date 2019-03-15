package stone.ast;
import java.util.List;

public class 二元表达式类 extends ASTList {
    public 二元表达式类(List<语法树类> c) { super(c); }
    public 语法树类 left() { return 子(0); }
    public String operator() {
        return ((语法树叶类)子(1)).词().取文本();
    }
    public 语法树类 right() { return 子(2); }
}
