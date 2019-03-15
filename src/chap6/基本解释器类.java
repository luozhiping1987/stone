package chap6;
import stone.*;
import stone.ast.语法树类;
import stone.ast.空声明类;

public class 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 基本语法分析器类(), new 基本环境类());
    }
    public static void run(基本语法分析器类 bp, 环境类 env)
        throws 分析例外
    {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        while (lexer.瞄(0) != 词类.EOF) {
            语法树类 t = bp.分析(lexer);
            if (!(t instanceof 空声明类)) {
                Object r = ((基本求值器类.语法树执行类)t).求值(env);
                System.out.println("=> " + r);
            }
        }
    }
}
