package stone.ast;
import java.util.List;

public class Dot extends Postfix {
  public static final String 关键字_新建 = "新建";

    public Dot(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().取文本(); }
    @Override
    public String toString() { return "." + name(); }
}
