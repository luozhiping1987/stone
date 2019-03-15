package chap12;
import java.util.ArrayList;
import java.util.List;
import static javassist.gluonj.GluonJ.revise;
import javassist.gluonj.*;
import stone.*;
import stone.ast.*;
import chap6.环境类;
import chap6.基本求值器类;
import chap6.基本求值器类.语法树执行类;
import chap7.函数求值器类.PrimaryEx;
import chap11.ArrayEnv;
import chap11.环境优化器类;
import chap11.Symbols;
import chap11.环境优化器类.语法树优化执行类;
import chap11.环境优化器类.环境执行类2;
import chap11.环境优化器类.ParamsEx;
import chap12.OptStoneObject.AccessException;

@Require(环境优化器类.class)
@Reviser public class 对象优化器类 {
    @Reviser public static class ClassStmntEx extends ClassStmnt {
        public ClassStmntEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {}
        public Object 求值(环境类 env) {
            Symbols methodNames = new MemberSymbols(((环境执行类2)env).所有符号(),
                                                    MemberSymbols.METHOD);
            Symbols fieldNames = new MemberSymbols(methodNames,
                                                   MemberSymbols.FIELD);
            OptClassInfo ci = new OptClassInfo(this, env, methodNames,
                                               fieldNames);
            ((环境执行类2)env).put(name(), ci);
            ArrayList<DefStmnt> methods = new ArrayList<DefStmnt>();
            if (ci.superClass() != null)
                ci.superClass().copyTo(fieldNames, methodNames, methods);
            Symbols newSyms = new SymbolThis(fieldNames);
            ((ClassBodyEx)body()).查找(newSyms, methodNames, fieldNames,
                                         methods);
            ci.setMethods(methods);
            return name();
        }
    }
    @Reviser public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env) {
            for (语法树类 t: this)
                if (!(t instanceof DefStmnt))
                    ((语法树执行类)t).求值(env);
            return null;
        }
        public void 查找(Symbols syms, Symbols methodNames,
                           Symbols fieldNames, ArrayList<DefStmnt> methods)
        {
            for (语法树类 t: this) {
                if (t instanceof DefStmnt) {
                    DefStmnt def = (DefStmnt)t;
                    int oldSize = methodNames.size();
                    int i = methodNames.putNew(def.name());
                    if (i >= oldSize)
                        methods.add(def);
                    else
                        methods.set(i, def);
                    ((DefStmntEx2)def).lookupAsMethod(fieldNames);
                }
                else
                    ((语法树优化执行类)t).查找(syms);
            }
        }
    }
    @Reviser public static class DefStmntEx2 extends 环境优化器类.DefStmntEx {
        public DefStmntEx2(List<语法树类> c) { super(c); }
        public int locals() { return size; }
        public void lookupAsMethod(Symbols syms) {
            Symbols newSyms = new Symbols(syms);
            newSyms.putNew(SymbolThis.NAME);
            ((ParamsEx)parameters()).查找(newSyms);
            ((语法树优化执行类)revise(body())).查找(newSyms);
            size = newSyms.size();
        }
    }
    @Reviser public static class DotEx extends Dot {
        public DotEx(List<语法树类> c) { super(c); }
        public Object 求值(环境类 env, Object value) {
            String member = name();
            if (value instanceof OptClassInfo) {
                if ("new".equals(member)) {
                    OptClassInfo ci = (OptClassInfo)value;
                    ArrayEnv newEnv = new ArrayEnv(1, ci.environment());
                    OptStoneObject so = new OptStoneObject(ci, ci.size());
                    newEnv.put(0, 0, so);
                    initObject(ci, so, newEnv);
                    return so;
                }
            }
            else if (value instanceof OptStoneObject) {
                try {
                    return ((OptStoneObject)value).read(member);
                } catch (AccessException e) {}
            }
            throw new StoneException("bad member access: " + member, this);
        }
        protected void initObject(OptClassInfo ci, OptStoneObject obj,
                                  环境类 env)
        {
            if (ci.superClass() != null)
                initObject(ci.superClass(), obj, env);
            ((ClassBodyEx)ci.body()).求值(env);
        }
    }
    @Reviser public static class NameEx2 extends 环境优化器类.NameEx {
        public NameEx2(词类 t) { super(t); }
        @Override public Object 求值(环境类 env) {
            if (index == UNKNOWN)
                return env.get(name());
            else if (nest == MemberSymbols.FIELD)
                return getThis(env).read(index);
            else if (nest == MemberSymbols.METHOD)
                return getThis(env).method(index);
            else
                return ((环境执行类2)env).get(nest, index);
        }
        @Override public void evalForAssign(环境类 env, Object value) {
            if (index == UNKNOWN)
                env.put(name(), value);
            else if (nest == MemberSymbols.FIELD)
                getThis(env).write(index, value);
            else if (nest == MemberSymbols.METHOD)
                throw new StoneException("cannot update a method: " + name(),
                                         this);
            else
                ((环境执行类2)env).put(nest, index, value);
        }
        protected OptStoneObject getThis(环境类 env) {
            return (OptStoneObject)((环境执行类2)env).get(0, 0);
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
                    if (t instanceof OptStoneObject)
                        return setField((OptStoneObject)t, (Dot)p.postfix(0),
                                        rvalue);
                }
            }
            return super.computeAssign(env, rvalue);
        }
        protected Object setField(OptStoneObject obj, Dot expr, Object rvalue) {
            String name = expr.name();
            try {
                obj.write(name, rvalue);
                return rvalue;
            } catch (AccessException e) {
                throw new StoneException("bad member access: " + name, this);
            }
        }
    }
}
