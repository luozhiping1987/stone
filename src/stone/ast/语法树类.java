package stone.ast;
import java.util.Iterator;

public abstract class 语法树类 implements Iterable<语法树类> {
    public abstract 语法树类 子(int i);
    public abstract int 子个数();
    public abstract Iterator<语法树类> children();
    public abstract String location();
    public Iterator<语法树类> iterator() { return children(); }
}
