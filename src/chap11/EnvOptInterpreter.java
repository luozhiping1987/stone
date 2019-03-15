package chap11;
import chap6.基本求值器类;
import chap6.Environment;
import chap8.Natives;
import stone.基本语法分析器类;
import stone.ClosureParser;
import stone.CodeDialog;
import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.ast.语法树类;
import stone.ast.NullStmnt;

public class EnvOptInterpreter {
    public static void main(String[] args) throws 分析例外 {
        run(new ClosureParser(),
            new Natives().environment(new ResizableArrayEnv()));
    }
    public static void run(基本语法分析器类 bp, Environment env)
        throws 分析例外
    {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        while (lexer.瞄(0) != 词类.EOF) {
            语法树类 t = bp.分析(lexer);
            if (!(t instanceof NullStmnt)) {
                ((EnvOptimizer.ASTreeOptEx)t).lookup(
                        ((EnvOptimizer.EnvEx2)env).symbols());
                Object r = ((基本求值器类.ASTreeEx)t).eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}
