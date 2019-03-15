package chap14;
import java.util.List;
import chap7.函数求值器类;
import chap11.环境优化器类;
import stone.词类;
import static javassist.gluonj.GluonJ.revise;
import stone.ast.*;
import javassist.gluonj.*;

@Require(TypedEvaluator.class)
@Reviser public class 类型检查器类 {
    @Reviser public static abstract class 语法树类型执行类 extends 语法树类 {
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            return null;
        }
    }
    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(词类 t) { super(t); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            return 类型信息类.INT;
        }
    }
    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(词类 t) { super(t); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            return 类型信息类.STRING;
        }
    }
    @Reviser public static class NameEx2 extends 环境优化器类.NameEx {
        protected 类型信息类 type;
        public NameEx2(词类 t) { super(t); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            type = tenv.get(nest, index);
            if (type == null)
                throw new 类型例外("undefined name: " + name(), this);
            else
                return type;
        }
        public 类型信息类 typeCheckForAssign(类型环境类 tenv, 类型信息类 valueType)
            throws 类型例外
        {
            type = tenv.get(nest, index);
            if (type == null) {
                type = valueType;
                tenv.put(0, index, valueType);
                return valueType;
            }
            else {
                valueType.assertSubtypeOf(type, tenv, this);
                return type;
            }
        }
    }
    @Reviser public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            类型信息类 t = ((语法树类型执行类)operand()).typeCheck(tenv);
            t.assertSubtypeOf(类型信息类.INT, tenv, this);
            return 类型信息类.INT;
        }
    }
    @Reviser public static class BinaryEx extends 二元表达式 {
        protected 类型信息类 leftType, rightType;
        public BinaryEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            String op = operator();
            if ("=".equals(op))
                return typeCheckForAssign(tenv);
            else {
                leftType = ((语法树类型执行类)left()).typeCheck(tenv);
                rightType = ((语法树类型执行类)right()).typeCheck(tenv);
                if ("+".equals(op))
                    return leftType.plus(rightType, tenv);
                else if ("==".equals(op))
                    return 类型信息类.INT;
                else {
                    leftType.assertSubtypeOf(类型信息类.INT, tenv, this);
                    rightType.assertSubtypeOf(类型信息类.INT, tenv, this);
                    return 类型信息类.INT;
                }
            }
        }
        protected 类型信息类 typeCheckForAssign(类型环境类 tenv)
            throws 类型例外
        {
            rightType = ((语法树类型执行类)right()).typeCheck(tenv);
            语法树类 le = left();
            if (le instanceof Name)
                return ((NameEx2)le).typeCheckForAssign(tenv, rightType);
            else
                throw new 类型例外("bad assignment", this);
        }
    }
    @Reviser public static class BlockEx extends BlockStmnt {
        类型信息类 type;
        public BlockEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            type = 类型信息类.INT;
            for (语法树类 t: this)
                if (!(t instanceof 空声明类))
                    type = ((语法树类型执行类)t).typeCheck(tenv);
            return type;
        }
    }
    @Reviser public static class IfEx extends IfStmnt {
        public IfEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            类型信息类 condType = ((语法树类型执行类)condition()).typeCheck(tenv);
            condType.assertSubtypeOf(类型信息类.INT, tenv, this);
            类型信息类 thenType = ((语法树类型执行类)thenBlock()).typeCheck(tenv);
            类型信息类 elseType;
            语法树类 elseBk = elseBlock();
            if (elseBk == null)
                elseType = 类型信息类.INT; 
            else
                elseType = ((语法树类型执行类)elseBk).typeCheck(tenv);
            return thenType.union(elseType, tenv);
        }
    }
    @Reviser public static class WhileEx extends While声明类 {
        public WhileEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            类型信息类 condType = ((语法树类型执行类)condition()).typeCheck(tenv);
            condType.assertSubtypeOf(类型信息类.INT, tenv, this);
            类型信息类 bodyType = ((语法树类型执行类)body()).typeCheck(tenv);
            return bodyType.union(类型信息类.INT, tenv);
        }
    }
    @Reviser public static class DefStmntEx2 extends TypedEvaluator.DefStmntEx {
        protected 类型信息类.FunctionType funcType;
        protected 类型环境类 bodyEnv;
        public DefStmntEx2(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            类型信息类[] params = ((ParamListEx2)parameters()).types();
            类型信息类 retType = 类型信息类.get(type());
            funcType = 类型信息类.function(retType, params);
            类型信息类 oldType = tenv.put(0, index, funcType);
            if (oldType != null)
                throw new 类型例外("function redefinition: " + name(),
                                        this);
            bodyEnv = new 类型环境类(size, tenv);
            for (int i = 0; i < params.length; i++)
                bodyEnv.put(0, i, params[i]);
            类型信息类 bodyType
                = ((语法树类型执行类)revise(body())).typeCheck(bodyEnv);
            bodyType.assertSubtypeOf(retType, tenv, this);
            return funcType;
        }
    }
    @Reviser
    public static class ParamListEx2 extends TypedEvaluator.ParamListEx {
        public ParamListEx2(List<语法树类> c) { super(c); }
        public 类型信息类[] types() throws 类型例外 {
            int s = size();
            类型信息类[] result = new 类型信息类[s];
            for (int i = 0; i < s; i++)
                result[i] = 类型信息类.get(typeTag(i));
            return result;
        }
    }
    @Reviser public static class PrimaryEx2 extends 函数求值器类.PrimaryEx {
        public PrimaryEx2(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            return typeCheck(tenv, 0);
        }
        public 类型信息类 typeCheck(类型环境类 tenv, int nest) throws 类型例外 {
            if (hasPostfix(nest)) {
                类型信息类 target = typeCheck(tenv, nest + 1);
                return ((PostfixEx)postfix(nest)).typeCheck(tenv, target); 
            }
            else
                return ((语法树类型执行类)operand()).typeCheck(tenv);
        }
    }
    @Reviser public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<语法树类> c) { super(c); }
        public abstract 类型信息类 typeCheck(类型环境类 tenv, 类型信息类 target)
            throws 类型例外;
    }
    @Reviser public static class ArgumentsEx extends Arguments {
        protected 类型信息类[] argTypes;
        protected 类型信息类.FunctionType funcType;
        public ArgumentsEx(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv, 类型信息类 target)
            throws 类型例外
        {
            if (!(target instanceof 类型信息类.FunctionType))
                throw new 类型例外("bad function", this);
            funcType = (类型信息类.FunctionType)target;
            类型信息类[] params = funcType.parameterTypes;
            if (size() != params.length)
                throw new 类型例外("bad number of arguments", this);
            argTypes = new 类型信息类[params.length];
            int num = 0;
            for (语法树类 a: this) {
                类型信息类 t = argTypes[num] = ((语法树类型执行类)a).typeCheck(tenv);
                t.assertSubtypeOf(params[num++], tenv, this);
            }
            return funcType.returnType;
        }
    }
    @Reviser public static class VarStmntEx2 extends TypedEvaluator.VarStmntEx {
        protected 类型信息类 varType, valueType;
        public VarStmntEx2(List<语法树类> c) { super(c); }
        public 类型信息类 typeCheck(类型环境类 tenv) throws 类型例外 {
            if (tenv.get(0, index) != null)
                throw new 类型例外("duplicate variable: " + name(), this);
            varType = 类型信息类.get(type());
            tenv.put(0, index, varType);
            valueType = ((语法树类型执行类)initializer()).typeCheck(tenv);
            valueType.assertSubtypeOf(varType, tenv, this);
            return varType;
        }
    }
}
