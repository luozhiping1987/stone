package chap11;
import chap6.基本求值器类;
import chap6.环境类;
import chap8.原生类;
import stone.基本语法分析器类;
import stone.闭包语法分析器类;
import stone.CodeDialog;
import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.ast.语法树类;
import stone.ast.空声明类;

public class 环境优化解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 闭包语法分析器类(),
            new 原生类().环境(new 可变长度数组环境类()));
    }
    public static void run(基本语法分析器类 bp, 环境类 env)
        throws 分析例外
    {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        while (lexer.瞄(0) != 词类.EOF) {
            语法树类 t = bp.分析(lexer);
            if (!(t instanceof 空声明类)) {
                ((环境优化器类.语法树优化执行类)t).lookup(
                        ((环境优化器类.环境执行类2)env).symbols());
                Object r = ((基本求值器类.语法树执行类)t).eval(env);
                System.out.println("=> " + r);
            }
        }
    }
}
