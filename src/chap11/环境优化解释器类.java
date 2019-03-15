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
                ((环境优化器类.语法树优化扩展类)t).查找(
                        ((环境优化器类.环境扩展类2)env).所有符号());
                Object r = ((基本求值器类.语法树扩展类)t).求值(env);
                System.out.println("=> " + r);
            }
        }
    }
}
