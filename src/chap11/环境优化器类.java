package chap11;
import static javassist.gluonj.GluonJ.revise;
import javassist.gluonj.*;
import java.util.List;
import stone.词类;
import stone.StoneException;
import stone.ast.*;
import chap11.Symbols.Location;
import chap6.环境类;
import chap6.基本求值器类;
import chap7.闭包求值器类;

@Require(闭包求值器类.class)
@Reviser public class 环境优化器类 {
    @Reviser public static interface 环境执行类2 extends 环境类 {
        Symbols symbols();
        void put(int nest, int index, Object value);
        Object get(int nest, int index);
        void putNew(String name, Object value);
        环境类 where(String name);
    }
    @Reviser public static abstract class 语法树优化执行类 extends 语法树类 {
        public void 查找(Symbols syms) {}
    }
    @Reviser public static class ASTListEx extends ASTList {
        public ASTListEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            for (语法树类 t: this)
                ((语法树优化执行类)t).查找(syms);
        }
    }
    @Reviser public static class DefStmntEx extends DefStmnt {
        protected int index, size;
        public DefStmntEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            index = syms.putNew(name());
            size = FunEx.lookup(syms, parameters(), body());
        }
        public Object 求值(环境类 env) {
            ((环境执行类2)env).put(0, index, new OptFunction(parameters(), body(),
                                                        env, size));
            return name();
        }
    }
    @Reviser public static class FunEx extends Fun {
        protected int size = -1;
        public FunEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            size = lookup(syms, parameters(), body());
        }
        public Object 求值(环境类 env) {
            return new OptFunction(parameters(), body(), env, size);
        }
        public static int lookup(Symbols syms, ParameterList params,
                                 BlockStmnt body)
        {
            Symbols newSyms = new Symbols(syms);
            ((ParamsEx)params).查找(newSyms);
            ((语法树优化执行类)revise(body)).查找(newSyms);
            return newSyms.size();
        }
    }
    @Reviser public static class ParamsEx extends ParameterList {
        protected int[] offsets = null;
        public ParamsEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            int s = size();
            offsets = new int[s];
            for (int i = 0; i < s; i++)
                offsets[i] = syms.putNew(name(i));
        }
        public void 求值(环境类 env, int index, Object value) {
            ((环境执行类2)env).put(0, offsets[index], value);
        }
    }
    @Reviser public static class NameEx extends Name {
        protected static final int UNKNOWN = -1;
        protected int nest, index;
        public NameEx(词类 t) { super(t); index = UNKNOWN; }
        public void 查找(Symbols syms) {
            Location loc = syms.get(name());
            if (loc == null)
                throw new StoneException("undefined name: " + name(), this);
            else {
                nest = loc.nest;
                index = loc.index;
            }
        }
        public void lookupForAssign(Symbols syms) {
            Location loc = syms.put(name());
            nest = loc.nest;
            index = loc.index;
        }
        public Object 求值(环境类 env) {
            if (index == UNKNOWN)
                return env.get(name());
            else
                return ((环境执行类2)env).get(nest, index);
        }
        public void evalForAssign(环境类 env, Object value) {
            if (index == UNKNOWN)
                env.put(name(), value);
            else
                ((环境执行类2)env).put(nest, index, value);
        }
    }
    @Reviser public static class BinaryEx2 extends 基本求值器类.BinaryEx {
        public BinaryEx2(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            语法树类 left = left();
            if ("=".equals(operator())) {
                if (left instanceof Name) {
                    ((NameEx)left).lookupForAssign(syms);
                    ((语法树优化执行类)right()).查找(syms);
                    return;
                }
            }
            ((语法树优化执行类)left).查找(syms);
            ((语法树优化执行类)right()).查找(syms);
        }
        @Override
        protected Object computeAssign(环境类 env, Object rvalue) {
            语法树类 l = left();
            if (l instanceof Name) {
                ((NameEx)l).evalForAssign(env, rvalue);
                return rvalue;
            }
            else
                return super.computeAssign(env, rvalue);
        }
    }
}
