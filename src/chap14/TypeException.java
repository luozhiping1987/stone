package chap14;
import stone.ast.语法树类;

public class TypeException extends Exception {
    public TypeException(String msg, 语法树类 t) {
        super(msg + " " + t.location()); 
    }
}
