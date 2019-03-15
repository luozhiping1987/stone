package chap14;

import stone.ast.语法树类;
import stone.ast.TypeTag;

public class 类型信息类 {
    public static final 类型信息类 ANY = new 类型信息类() {
        @Override public String toString() { return "Any"; } 
    };
    public static final 类型信息类 INT = new 类型信息类() {
        @Override public String toString() { return "Int"; } 
    };
    public static final 类型信息类 STRING = new 类型信息类() {
        @Override public String toString() { return "String"; } 
    };

    public 类型信息类 type() { return this; }
    public boolean match(类型信息类 obj) {
        return type() == obj.type();
    }
    public boolean subtypeOf(类型信息类 superType) {
        superType = superType.type();
        return type() == superType || superType == ANY;
    }
    public void assertSubtypeOf(类型信息类 type, 类型环境类 env, 语法树类 where)
        throws 类型例外
    {
        if (!subtypeOf(type))
            throw new 类型例外("type mismatch: cannot convert from "
                                    + this + " to " + type, where);
    }
    public 类型信息类 union(类型信息类 right, 类型环境类 tenv) {
        if (match(right))
            return type();
        else
            return ANY;
    }
    public 类型信息类 plus(类型信息类 right, 类型环境类 tenv) {
        if (INT.match(this) && INT.match(right))
            return INT;
        else if (STRING.match(this) || STRING.match(right))
            return STRING;
        else
            return ANY;
    }
    public static 类型信息类 get(TypeTag tag) throws 类型例外 {
        String tname = tag.type();
        if (INT.toString().equals(tname))
            return INT;
        else if (STRING.toString().equals(tname))
            return STRING;
        else if (ANY.toString().equals(tname))
            return ANY;
        else if (TypeTag.UNDEF.equals(tname))
            return new UnknownType();
        else
            throw new 类型例外("unknown type " + tname, tag);
    }
    public static FunctionType function(类型信息类 ret, 类型信息类... params) {
        return new FunctionType(ret, params);
    }
    public boolean isFunctionType() { return false; }
    public FunctionType toFunctionType() { return null; }
    public boolean isUnknownType() { return false; }
    public UnknownType toUnknownType() { return null; }
    public static class UnknownType extends 类型信息类 {
        @Override public 类型信息类 type() { return ANY; }
        @Override public String toString() { return type().toString(); }
        @Override public boolean isUnknownType() { return true; }
        @Override public UnknownType toUnknownType() { return this; }
    }
    public static class FunctionType extends 类型信息类 {
        public 类型信息类 returnType;
        public 类型信息类[] parameterTypes;
        public FunctionType(类型信息类 ret, 类型信息类... params) {
            returnType = ret;
            parameterTypes = params;
        }
        @Override public boolean isFunctionType() { return true; }
        @Override public FunctionType toFunctionType() { return this; }
        @Override public boolean match(类型信息类 obj) {
            if (!(obj instanceof FunctionType))
                return false;
            FunctionType func = (FunctionType)obj;
            if (parameterTypes.length != func.parameterTypes.length)
                return false;
            for (int i = 0; i < parameterTypes.length; i++)
                if (!parameterTypes[i].match(func.parameterTypes[i]))
                    return false;
            return returnType.match(func.returnType);
        }
        @Override public String toString() {
            StringBuilder sb = new StringBuilder();
            if (parameterTypes.length == 0)
                sb.append("Unit");
            else
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0)
                        sb.append(" * ");
                    sb.append(parameterTypes[i]);
                }
            sb.append(" -> ").append(returnType);
            return sb.toString();
        }
    }
}
