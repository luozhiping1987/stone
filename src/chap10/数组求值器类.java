package chap10;
import java.util.List;
import javassist.gluonj.*;
import stone.ArrayParser;
import stone.StoneException;
import stone.ast.*;
import chap6.环境类;
import chap6.基本求值器类;
import chap6.基本求值器类.语法树执行类;
import chap7.函数求值器类;
import chap7.函数求值器类.PrimaryEx;

@Require({函数求值器类.class, ArrayParser.class})
@Reviser public class 数组求值器类 {
    @Reviser public static class ArrayLitEx extends ArrayLiteral {
        public ArrayLitEx(List<语法树类> list) { super(list); }
        public Object 求值(环境类 env) {
            int s = 子个数();
            Object[] res = new Object[s];
            int i = 0;
            for (语法树类 t: this)
                res[i++] = ((语法树执行类)t).求值(env);
            return res;
        }
    }
    @Reviser public static class ArrayRefEx extends ArrayRef {
        public ArrayRefEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env, Object value) {
            if (value instanceof Object[]) {
                Object index = ((语法树执行类)index()).求值(env);
                if (index instanceof Integer)
                    return ((Object[])value)[(Integer)index];
            }

            throw new StoneException("bad array access", this);
        }
    }
    @Reviser public static class AssignEx extends 基本求值器类.BinaryEx {
        public AssignEx(List<语法树类> c) { super(c); }
        @Override
        protected Object computeAssign(环境类 env, Object rvalue) {
            语法树类 le = left();
            if (le instanceof PrimaryExpr) {
                PrimaryEx p = (PrimaryEx)le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                    Object a = ((PrimaryEx)le).evalSubExpr(env, 1);
                    if (a instanceof Object[]) {
                        ArrayRef aref = (ArrayRef)p.postfix(0);
                        Object index = ((语法树执行类)aref.index()).求值(env);
                        if (index instanceof Integer) {
                            ((Object[])a)[(Integer)index] = rvalue;
                            return rvalue;
                        }
                    }
                    throw new StoneException("bad array access", this);
                }
            }
            return super.computeAssign(env, rvalue);
        }
    }       
}
