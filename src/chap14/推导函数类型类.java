package chap14;
import java.util.List;
import chap14.类型信息类.FunctionType;
import chap14.类型信息类.UnknownType;
import chap14.InferTypes.UnknownTypeEx;
import stone.ast.语法树类;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

@Require({类型检查器类.class, InferTypes.class})
@Reviser public class 推导函数类型类 {
    @Reviser public static class DefStmntEx3 extends 类型检查器类.DefStmntEx2 {
        public DefStmntEx3(List<语法树类> c) { super(c); }
        @Override public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            FunctionType func = super.typeCheck(tenv).toFunctionType();
            for (类型信息类 t: func.parameterTypes)
                fixUnknown(t);
            fixUnknown(func.returnType);
            return func;
        }
        protected void fixUnknown(类型信息类 t) {
            if (t.isUnknownType()) {
                UnknownType ut = t.toUnknownType();
                if (!((UnknownTypeEx)ut).resolved())
                    ((UnknownTypeEx)ut).setType(类型信息类.ANY);
            }
        }
    }
}
