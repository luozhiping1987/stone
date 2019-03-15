package chap14;
import java.util.ArrayList;
import java.util.List;
import chap11.ArrayEnv;
import chap11.环境优化器类;
import chap6.环境类;
import chap7.函数求值器类;
import stone.StoneException;
import stone.词类;
import stone.ast.*;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import static javassist.gluonj.GluonJ.revise;

@Require(类型检查器类.class)
@Reviser public class 变Java类 {
    public static final String METHOD = "m";
    public static final String LOCAL = "v";
    public static final String ENV = "env";
    public static final String RESULT = "res";
    public static final String ENV_TYPE = "chap11.ArrayEnv";

    public static String translateExpr(语法树类 ast, 类型信息类 from, 类型信息类 to) {
        return translateExpr(((ASTreeEx)ast).translate(null), from, to);
    }
    public static String translateExpr(String expr, 类型信息类 from,
                                       类型信息类 to)
    {
        from = from.type();
        to = to.type();
        if (from == 类型信息类.INT) {
            if (to == 类型信息类.ANY)
                return "new Integer(" + expr + ")";
            else if (to == 类型信息类.STRING)
                return "Integer.toString(" + expr + ")";
        }
        else if (from == 类型信息类.ANY)
            if (to == 类型信息类.STRING)
                return expr + ".toString()";
            else if (to == 类型信息类.INT)
                return "((Integer)" + expr + ").intValue()";
        return expr;
    }
    public static String returnZero(类型信息类 to) {
        if (to.type() == 类型信息类.ANY)
            return RESULT + "=new Integer(0);";
        else
            return RESULT + "=0;";
    }

    @Reviser public static interface EnvEx3 extends 环境优化器类.环境扩展类2 {
        JavaLoader javaLoader();
    }
    @Reviser public static class ArrayEnvEx extends ArrayEnv {
        public ArrayEnvEx(int size, 环境类 out) { super(size, out); }
        protected JavaLoader jloader = new JavaLoader();
        public JavaLoader javaLoader() { return jloader; }
    }
    @Reviser public static abstract class ASTreeEx extends 语法树类 {
        public String translate(类型信息类 result) { return ""; }
    }
    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(词类 t) { super(t); }
        public String translate(类型信息类 result) {
            return Integer.toString(value());
        }
    }
    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(词类 t) { super(t); }
        public String translate(类型信息类 result) {
            StringBuilder code = new StringBuilder();
            String literal = value();
            code.append('"');
            for (int i = 0; i < literal.length(); i++) {
                char c = literal.charAt(i);
                if (c == '"')
                    code.append("\\\"");
                else if (c == '\\')
                    code.append("\\\\");
                else if (c == '\n')
                    code.append("\\n");
                else
                    code.append(c);
            }
            code.append('"');
            return code.toString();
        }
    }
    @Reviser public static class NameEx3 extends 类型检查器类.NameEx2 {
        public NameEx3(词类 t) { super(t); }
        public String translate(类型信息类 result) {
            if (type.isFunctionType())
                return JavaFunction.className(name()) + "." + METHOD;
            else if (nest == 0)
                return LOCAL + index;
            else {
                String expr = ENV + ".get(0," + index + ")";
                return translateExpr(expr, 类型信息类.ANY, type);
            }
        }
        public String translateAssign(类型信息类 valueType, 语法树类 right) {
            if (nest == 0)
                return "(" + LOCAL + index + "="
                       + translateExpr(right, valueType, type) + ")";
            else {
                String value = ((ASTreeEx)right).translate(null);
                return "chap14.Runtime.write" + type.toString()
                       + "(" + ENV + "," + index + "," + value + ")";
            }
        }
    }
    @Reviser public static class NegativeEx extends NegativeExpr {
        public NegativeEx(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            return "-" + ((ASTreeEx)operand()).translate(null);
        }
    }
    @Reviser public static class BinaryEx2 extends 类型检查器类.BinaryEx {
        public BinaryEx2(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            String op = operator();
            if ("=".equals(op))
                return ((NameEx3)left()).translateAssign(rightType, right());
            else if (leftType.type() != 类型信息类.INT
                     || rightType.type() != 类型信息类.INT) {
                String e1 = translateExpr(left(), leftType, 类型信息类.ANY);
                String e2 = translateExpr(right(), rightType, 类型信息类.ANY);
                if ("==".equals(op))
                    return "chap14.Runtime.eq(" + e1 + "," + e2 + ")";
                else if ("+".equals(op)) {
                    if (leftType.type() == 类型信息类.STRING
                        || rightType.type() == 类型信息类.STRING)
                        return e1 + "+" + e2;
                    else
                        return "chap14.Runtime.plus(" + e1 + "," + e2 + ")";
                }
                else
                    throw new StoneException("bad operator", this);
            }
            else {
                String expr = ((ASTreeEx)left()).translate(null) + op
                              + ((ASTreeEx)right()).translate(null);
                if ("<".equals(op) || ">".equals(op) || "==".equals(op))
                    return "(" + expr + "?1:0)";
                else
                    return "(" + expr + ")";
            }
        }
    }
    @Reviser public static class BlockEx2 extends 类型检查器类.BlockEx {
        public BlockEx2(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            ArrayList<语法树类> body = new ArrayList<语法树类>();
            for (语法树类 t: this)
                if (!(t instanceof 空声明类))
                    body.add(t);
            StringBuilder code = new StringBuilder();
            if (result != null && body.size() < 1)
                code.append(returnZero(result));
            else
                for (int i = 0; i < body.size(); i++)
                    translateStmnt(code, body.get(i), result,
                                   i == body.size() - 1);
            return code.toString();
        }
        protected void translateStmnt(StringBuilder code, 语法树类 tree,
                                      类型信息类 result, boolean last)
        {
            if (isControlStmnt(tree))
                code.append(((ASTreeEx)tree).translate(last ? result : null));
            else 
                if (last && result != null)
                    code.append(RESULT).append('=')
                        .append(translateExpr(tree, type, result)).append(";\n");
                else if (isExprStmnt(tree))
                    code.append(((ASTreeEx)tree).translate(null)).append(";\n");
                else
                    throw new StoneException("bad expression statement", this); 
        }
        protected static boolean isExprStmnt(语法树类 tree) {
            if (tree instanceof 二元表达式类)
                return "=".equals(((二元表达式类)tree).operator());
            return tree instanceof PrimaryExpr || tree instanceof VarStmnt;
        }
        protected static boolean isControlStmnt(语法树类 tree) {
            return tree instanceof BlockStmnt || tree instanceof IfStmnt
                   || tree instanceof While声明类;
        }
    }
    @Reviser public static class IfEx extends IfStmnt {
        public IfEx(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            StringBuilder code = new StringBuilder();       
            code.append("if(");
            code.append(((ASTreeEx)condition()).translate(null));
            code.append("!=0){\n");
            code.append(((ASTreeEx)thenBlock()).translate(result));
            code.append("} else {\n");
            语法树类 elseBk = elseBlock();
            if (elseBk != null)
                code.append(((ASTreeEx)elseBk).translate(result));
            else if (result != null)
                code.append(returnZero(result));
            return code.append("}\n").toString();
        }
    }
    @Reviser public static class WhileEx extends While声明类 {
        public WhileEx(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            String code = "while(" + ((ASTreeEx)condition()).translate(null)
                          + "!=0){\n" + ((ASTreeEx)body()).translate(result)
                          + "}\n";
            if (result == null)
                return code;
            else
                return returnZero(result) + "\n" + code;
        }
    }
    @Reviser public static class DefStmntEx3 extends 类型检查器类.DefStmntEx2 {
        public DefStmntEx3(List<语法树类> c) { super(c); }
        @Override public Object 求值(环境类 env) {
            String funcName = name();
            JavaFunction func = new JavaFunction(funcName, translate(null),
                                                 ((EnvEx3)env).javaLoader());
            ((EnvEx3)env).putNew(funcName, func);
            return funcName;
        }
        public String translate(类型信息类 result) {
            StringBuilder code = new StringBuilder("public static ");
            类型信息类 returnType = funcType.returnType;
            code.append(javaType(returnType)).append(' ');
            code.append(METHOD).append("(chap11.ArrayEnv ").append(ENV);
            for (int i = 0; i < funcType.parameterTypes.length; i++) {
                code.append(',').append(javaType(funcType.parameterTypes[i]))
                    .append(' ').append(LOCAL).append(i);
            }
            code.append("){\n");
            code.append(javaType(returnType)).append(' ').append(RESULT)
                .append(";\n");
            for (int i = funcType.parameterTypes.length; i < size; i++) {
                类型信息类 t = bodyEnv.get(0, i);
                code.append(javaType(t)).append(' ').append(LOCAL).append(i);
                if (t.type() == 类型信息类.INT)
                    code.append("=0;\n");
                else
                    code.append("=null;\n");
            }
            code.append(((ASTreeEx)revise(body())).translate(returnType));
            code.append("return ").append(RESULT).append(";}");
            return code.toString();
        }
        protected String javaType(类型信息类 t) {
            if (t.type() == 类型信息类.INT)
                return "int";
            else if (t.type() == 类型信息类.STRING)
                return "String";
            else
                return "Object";
        }
    }
    @Reviser public static class PrimaryEx2 extends 函数求值器类.PrimaryEx {
        public PrimaryEx2(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) { return translate(0); }
        public String translate(int nest) {
            if (hasPostfix(nest)) {
                String expr = translate(nest + 1);
                return ((PostfixEx)postfix(nest)).translate(expr);
            }
            else
                return ((ASTreeEx)operand()).translate(null);
        }
    }
    @Reviser public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<语法树类> c) { super(c); }
        public abstract String translate(String expr);
    }
    @Reviser public static class ArgumentsEx extends 类型检查器类.ArgumentsEx {
        public ArgumentsEx(List<语法树类> c) { super(c); }
        public String translate(String expr) {
            StringBuilder code = new StringBuilder(expr);
            code.append('(').append(ENV);
            for (int i = 0; i < size(); i++)
                code.append(',')
                    .append(translateExpr(子(i), argTypes[i],
                                          funcType.parameterTypes[i]));
            return code.append(')').toString();
        }
        public Object 求值(环境类 env, Object value) {
            if (!(value instanceof JavaFunction))
                throw new StoneException("bad function", this);
            JavaFunction func = (JavaFunction)value;
            Object[] args = new Object[子个数() + 1];
            args[0] = env;
            int num = 1;
            for (语法树类 a: this)
                args[num++] = ((chap6.基本求值器类.语法树扩展类)a).求值(env); 
            return func.invoke(args);
        }
    }
    @Reviser public static class VarStmntEx3 extends 类型检查器类.VarStmntEx2 {
        public VarStmntEx3(List<语法树类> c) { super(c); }
        public String translate(类型信息类 result) {
            return LOCAL + index + "="
                   + translateExpr(initializer(), valueType, varType);
        }
    }
}
