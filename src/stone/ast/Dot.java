package stone.ast;
import java.util.List;

public class Dot extends Postfix {
    public Dot(List<语法树类> c) { super(c); }
    public String name() { return ((语法树叶类)子(0)).词().getText(); }
    public String toString() { return "." + name(); } 
}
