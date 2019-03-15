package chap8;
import java.util.List;
import stone.StoneException;
import stone.ast.语法树类;
import javassist.gluonj.*;
import chap6.环境类;
import chap6.基本求值器类.语法树执行类;
import chap7.函数求值器类;

@Require(函数求值器类.class)
@Reviser public class 原生求值器类 {
    @Reviser public static class NativeArgEx extends 函数求值器类.ArgumentsEx {
        public NativeArgEx(List<语法树类> c) { super(c); }
        @Override public Object eval(环境类 callerEnv, Object value) {
            if (!(value instanceof NativeFunction))
                return super.eval(callerEnv, value);

            NativeFunction func = (NativeFunction)value;
            int nparams = func.numOfParameters();
            if (size() != nparams)
                throw new StoneException("bad number of arguments", this);
            Object[] args = new Object[nparams];
            int num = 0;
            for (语法树类 a: this) {
                语法树执行类 ae = (语法树执行类)a;
                args[num++] = ae.eval(callerEnv);
            }
            return func.invoke(args, this);
        }
    }
}
