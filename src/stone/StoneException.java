package stone;
import stone.ast.语法树类;

public class StoneException extends RuntimeException {
    public StoneException(String m) { super(m); }
    public StoneException(String m, 语法树类 t) {
        super(m + " " + t.location());
    }
}
