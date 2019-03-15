package stone.ast;
import java.util.List;

public class Arguments extends Postfix {
    public Arguments(List<语法树类> c) { super(c); }
    public int size() { return 子个数(); }
}
