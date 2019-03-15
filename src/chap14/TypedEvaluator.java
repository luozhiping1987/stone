package chap14;
import java.util.List;
import javassist.gluonj.*;
import stone.ast.*;
import chap11.环境优化器类;
import chap11.Symbols;
import chap11.环境优化器类.语法树优化执行类;
import chap6.环境类;
import chap6.基本求值器类.语法树执行类;

@Require(环境优化器类.class)
@Reviser public class TypedEvaluator {
    @Reviser public static class DefStmntEx extends 环境优化器类.DefStmntEx {
        public DefStmntEx(List<语法树类> c) { super(c); }
        public TypeTag type() { return (TypeTag)子(2); }
        @Override public BlockStmnt body() { return (BlockStmnt)子(3); }
        @Override public String toString() {
            return "(def " + name() + " " + parameters() + " " + type() + " "
                   + body() + ")";
        }
    }
    @Reviser public static class ParamListEx extends 环境优化器类.ParamsEx {
        public ParamListEx(List<语法树类> c) { super(c); }
        @Override public String name(int i) {
            return ((语法树叶类)子(i).子(0)).词().取文本();
        }
        public TypeTag typeTag(int i) {
            return (TypeTag)子(i).子(1);
        }
    }
    @Reviser public static class VarStmntEx extends VarStmnt {
        protected int index;
        public VarStmntEx(List<语法树类> c) { super(c); }
        public void 查找(Symbols syms) {
            index = syms.putNew(name());
            ((语法树优化执行类)initializer()).查找(syms);
        }
        public Object 求值(环境类 env) {
            Object value = ((语法树执行类)initializer()).求值(env);
            ((环境优化器类.环境执行类2)env).put(0, index, value);
            return value;
        }
    }
}
