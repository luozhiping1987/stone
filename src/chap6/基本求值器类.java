package chap6;
import javassist.gluonj.*;
import stone.词类;
import stone.StoneException;
import stone.ast.*;
import java.util.List;

@Reviser public class 基本求值器类 {
    public static final int TRUE = 1;
    public static final int FALSE = 0;
    @Reviser public static abstract class 语法树执行类 extends 语法树类 {
        public abstract Object 求值(环境类 env);
    }
    @Reviser public static class ASTListEx extends ASTList {
        public ASTListEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }
    @Reviser public static class ASTLeafEx extends 语法树叶类 {
        public ASTLeafEx(词类 t) { super(t); }
        public Object 求值(环境类 env) {
            throw new StoneException("cannot eval: " + toString(), this);
        }
    }
    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(词类 t) { super(t); }
        public Object 求值(环境类 e) { return value(); }
    }
    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(词类 t) { super(t); }
        public Object 求值(环境类 e) { return value(); }
    }
    @Reviser public static class NameEx extends Name {
        public NameEx(词类 t) { super(t); }
        public Object 求值(环境类 env) {
            Object value = env.get(name());
            if (value == null)
                throw new StoneException("undefined name: " + name(), this);
            else
                return value;
        }
    }
    @Reviser public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            Object v = ((语法树执行类)operand()).求值(env);
            if (v instanceof Integer)
                return new Integer(-((Integer)v).intValue());
            else
                throw new StoneException("bad type for -", this);
        }
    }
    @Reviser public static class BinaryEx extends 二元表达式类 {
        public BinaryEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            String op = operator();
            if ("=".equals(op)) {
                Object right = ((语法树执行类)right()).求值(env);
                return computeAssign(env, right);
            }
            else {
                Object left = ((语法树执行类)left()).求值(env);
                Object right = ((语法树执行类)right()).求值(env);
                return computeOp(left, op, right);
            }
        }
        protected Object computeAssign(环境类 env, Object rvalue) {
            语法树类 l = left();
            if (l instanceof Name) {
                env.put(((Name)l).name(), rvalue);
                return rvalue;
            }
            else
                throw new StoneException("bad assignment", this);
        }
        protected Object computeOp(Object left, String op, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return computeNumber((Integer)left, op, (Integer)right);
            }
            else
                if (op.equals("+"))
                    return String.valueOf(left) + String.valueOf(right);
                else if (op.equals("==")) {
                    if (left == null)
                        return right == null ? TRUE : FALSE;
                    else
                        return left.equals(right) ? TRUE : FALSE;
                }
                else
                    throw new StoneException("bad type", this);
        }
        protected Object computeNumber(Integer left, String op, Integer right) {
            int a = left.intValue(); 
            int b = right.intValue();
            if (op.equals("+"))
                return a + b;
            else if (op.equals("-"))
                return a - b;
            else if (op.equals("*"))
                return a * b;
            else if (op.equals("/"))
                return a / b;
            else if (op.equals("%"))
                return a % b;
            else if (op.equals("=="))
                return a == b ? TRUE : FALSE;
            else if (op.equals(">"))
                return a > b ? TRUE : FALSE;
            else if (op.equals("<"))
                return a < b ? TRUE : FALSE;
            else
                throw new StoneException("bad operator", this);
        }
    }
    @Reviser public static class BlockEx extends BlockStmnt {
        public BlockEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            Object result = 0;
            for (语法树类 t: this) {
                if (!(t instanceof 空声明类))
                    result = ((语法树执行类)t).求值(env);
            }
            return result;
        }
    }
    @Reviser public static class IfEx extends IfStmnt {
        public IfEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            Object c = ((语法树执行类)condition()).求值(env);
            if (c instanceof Integer && ((Integer)c).intValue() != FALSE)
                return ((语法树执行类)thenBlock()).求值(env);
            else {
                语法树类 b = elseBlock();
                if (b == null)
                    return 0;
                else
                    return ((语法树执行类)b).求值(env);
            }
        }
    }
    @Reviser public static class WhileEx extends While声明类 {
        public WhileEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            Object result = 0;
            for (;;) {
                Object c = ((语法树执行类)condition()).求值(env);
                if (c instanceof Integer && ((Integer)c).intValue() == FALSE)
                    return result;
                else
                    result = ((语法树执行类)body()).求值(env);
            }
        }
    }
}
