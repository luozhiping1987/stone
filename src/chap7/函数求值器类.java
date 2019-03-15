package chap7;
import java.util.List;
import javassist.gluonj.*;
import stone.StoneException;
import stone.ast.*;
import chap6.基本求值器类;
import chap6.环境类;
import chap6.基本求值器类.语法树执行类;
import chap6.基本求值器类.BlockEx;

@Require(基本求值器类.class)
@Reviser public class 函数求值器类 {
    @Reviser public static interface EnvEx extends 环境类 {
        void putNew(String name, Object value);
        环境类 where(String name);
        void setOuter(环境类 e);
    }
    @Reviser public static class DefStmntEx extends DefStmnt {
        public DefStmntEx(List<语法树类> c) { super(c); }
        public Object eval(环境类 env) {
            ((EnvEx)env).putNew(name(), new Function(parameters(), body(), env));
            return name();
        }
    }
    @Reviser public static class PrimaryEx extends PrimaryExpr {
        public PrimaryEx(List<语法树类> c) { super(c); }
        public 语法树类 operand() { return 子(0); }
        public Postfix postfix(int nest) {
            return (Postfix)子(子个数() - nest - 1);
        }
        public boolean hasPostfix(int nest) { return 子个数() - nest > 1; } 
        public Object eval(环境类 env) {
            return evalSubExpr(env, 0);
        }
        public Object evalSubExpr(环境类 env, int nest) {
            if (hasPostfix(nest)) {
                Object target = evalSubExpr(env, nest + 1);
                return ((PostfixEx)postfix(nest)).eval(env, target);
            }
            else
                return ((语法树执行类)operand()).eval(env);
        }
    }
    @Reviser public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<语法树类> c) { super(c); }
        public abstract Object eval(环境类 env, Object value);
    }
    @Reviser public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<语法树类> c) { super(c); }
        public Object eval(环境类 callerEnv, Object value) {
            if (!(value instanceof Function))
                throw new StoneException("bad function", this);
            Function func = (Function)value;
            ParameterList params = func.parameters();
            if (size() != params.size())
                throw new StoneException("bad number of arguments", this);
            环境类 newEnv = func.makeEnv();
            int num = 0;
            for (语法树类 a: this)
                ((ParamsEx)params).eval(newEnv, num++,
                                        ((语法树执行类)a).eval(callerEnv));
            return ((BlockEx)func.body()).eval(newEnv);
        }
    }
    @Reviser public static class ParamsEx extends ParameterList {
        public ParamsEx(List<语法树类> c) { super(c); }
        public void eval(环境类 env, int index, Object value) {
            ((EnvEx)env).putNew(name(index), value);
        }
    }
}
