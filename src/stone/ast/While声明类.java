package stone.ast;
import java.util.List;

public class While声明类 extends ASTList {
    public While声明类(List<语法树类> c) { super(c); }
    public 语法树类 condition() { return 子(0); }
    public 语法树类 body() { return 子(1); }
    public String toString() {
        return "(while " + condition() + " " + body() + ")";
    }
}
