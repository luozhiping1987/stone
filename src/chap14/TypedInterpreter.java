package chap14;
import stone.基本语法分析器类;
import stone.CodeDialog;
import stone.词法分析器类;
import stone.词类;
import stone.TypedParser;
import stone.分析例外;
import stone.ast.语法树类;
import stone.ast.NullStmnt;
import chap11.环境优化器类;
import chap11.可变长度数组环境类;
import chap6.基本求值器类;
import chap6.Environment;

public class TypedInterpreter {
    public static void main(String[] args) throws 分析例外, TypeException {
        TypeEnv te = new TypeEnv();
        run(new TypedParser(),
            new TypedNatives(te).环境(new 可变长度数组环境类()),
            te);
    }
    public static void run(基本语法分析器类 bp, Environment env, TypeEnv typeEnv)
        throws 分析例外, TypeException
    {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        while (lexer.瞄(0) != 词类.EOF) {
            语法树类 tree = bp.分析(lexer);
            if (!(tree instanceof NullStmnt)) {
                ((环境优化器类.ASTreeOptEx)tree).lookup(
                                        ((环境优化器类.EnvEx2)env).symbols());
                TypeInfo type
                    = ((TypeChecker.ASTreeTypeEx)tree).typeCheck(typeEnv);
                Object r = ((基本求值器类.ASTreeEx)tree).eval(env);
                System.out.println("=> " + r + " : " + type);
            }
        }
    }
}
