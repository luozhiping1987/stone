package chap14;
import java.util.List;
import chap14.TypeInfo.FunctionType;
import chap14.TypeInfo.UnknownType;
import chap14.InferTypes.UnknownTypeEx;
import stone.ast.语法树类;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

@Require({TypeChecker.class, InferTypes.class})
@Reviser public class 推导函数类型类 {
    @Reviser public static class DefStmntEx3 extends TypeChecker.DefStmntEx2 {
        public DefStmntEx3(List<语法树类> c) { super(c); }
        @Override public TypeInfo typeCheck(类型环境类 tenv) throws TypeException {
            FunctionType func = super.typeCheck(tenv).toFunctionType();
            for (TypeInfo t: func.parameterTypes)
                fixUnknown(t);
            fixUnknown(func.returnType);
            return func;
        }
        protected void fixUnknown(TypeInfo t) {
            if (t.isUnknownType()) {
                UnknownType ut = t.toUnknownType();
                if (!((UnknownTypeEx)ut).resolved())
                    ((UnknownTypeEx)ut).setType(TypeInfo.ANY);
            }
        }
    }
}
