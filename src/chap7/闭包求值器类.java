package chap7;
import java.util.List;
import javassist.gluonj.*;
import stone.ast.语法树类;
import stone.ast.Fun;
import chap6.环境类;

@Require(函数求值器类.class)
@Reviser public class 闭包求值器类 {
    @Reviser public static class FunEx extends Fun {
        public FunEx(List<语法树类> c) { super(c); }
        public Object eval(环境类 env) {
            return new Function(parameters(), body(), env);
        }
    }
}
