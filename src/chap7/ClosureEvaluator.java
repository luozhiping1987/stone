package chap7;
import java.util.List;
import javassist.gluonj.*;
import stone.ast.语法树类;
import stone.ast.Fun;
import chap6.Environment;

@Require(函数求值器类.class)
@Reviser public class ClosureEvaluator {
    @Reviser public static class FunEx extends Fun {
        public FunEx(List<语法树类> c) { super(c); }
        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}
