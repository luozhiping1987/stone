package stone.ast;
import java.util.List;

public class While声明类 extends ASTList {
  public static final String 关键字 = "每当";

    public While声明类(List<语法树类> c) { super(c); }
    public 语法树类 condition() { return 子(0); }
    public 语法树类 body() { return 子(1); }
    @Override
    public String toString() {
        return "(" + 关键字 + " " + condition() + " " + body() + ")";
    }
}
