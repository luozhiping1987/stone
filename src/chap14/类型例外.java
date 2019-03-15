package chap14;
import stone.ast.语法树类;

public class 类型例外 extends Exception {
    public 类型例外(String msg, 语法树类 t) {
        super(msg + " " + t.location()); 
    }
}
