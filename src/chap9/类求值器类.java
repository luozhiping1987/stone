package chap9;
import java.util.List;
import stone.StoneException;
import javassist.gluonj.*;
import stone.ast.*;
import chap6.环境类;
import chap6.基本求值器类.语法树执行类;
import chap6.基本求值器类;
import chap7.函数求值器类;
import chap7.嵌套环境类;
import chap7.函数求值器类.EnvEx;
import chap7.函数求值器类.PrimaryEx;
import chap9.StoneObject.AccessException;

@Require(函数求值器类.class)
@Reviser public class 类求值器类 {
    @Reviser public static class ClassStmntEx extends ClassStmnt {
        public ClassStmntEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            ClassInfo ci = new ClassInfo(this, env);
            ((EnvEx)env).put(name(), ci);
            return name();
        }
    }
    @Reviser public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            for (语法树类 t: this)
                ((语法树执行类)t).求值(env);
            return null;
        }
    }
    @Reviser public static class DotEx extends Dot {
        public DotEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env, Object value) {
            String member = name();
            if (value instanceof ClassInfo) {
                if ("new".equals(member)) {
                    ClassInfo ci = (ClassInfo)value;
                    嵌套环境类 e = new 嵌套环境类(ci.environment());
                    StoneObject so = new StoneObject(e);
                    e.putNew("this", so);
                    initObject(ci, e);
                    return so;
                }
            }
            else if (value instanceof StoneObject) {
                try {
                    return ((StoneObject)value).read(member);
                } catch (AccessException e) {}
            }
            throw new StoneException("bad member access: " + member, this);
        }
        protected void initObject(ClassInfo ci, 环境类 env) {
            if (ci.superClass() != null)
                initObject(ci.superClass(), env);
            ((ClassBodyEx)ci.body()).求值(env);
        }
    }
    @Reviser public static class AssignEx extends 基本求值器类.BinaryEx {
        public AssignEx(List<语法树类> c) { super(c); }
        @Override
        protected Object computeAssign(环境类 env, Object rvalue) {
            语法树类 le = left();
            if (le instanceof PrimaryExpr) {
                PrimaryEx p = (PrimaryEx)le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                    Object t = ((PrimaryEx)le).evalSubExpr(env, 1);
                    if (t instanceof StoneObject)
                        return setField((StoneObject)t, (Dot)p.postfix(0),
                                        rvalue);
                }
            }
            return super.computeAssign(env, rvalue);
        }
        protected Object setField(StoneObject obj, Dot expr, Object rvalue) {
            String name = expr.name();
            try {
                obj.write(name, rvalue);
                return rvalue;
            } catch (AccessException e) {
                throw new StoneException("bad member access " + location()
                                         + ": " + name);
            }
        }
    }
}
