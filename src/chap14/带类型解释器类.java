package chap14;
import stone.基本语法分析器类;
import stone.CodeDialog;
import stone.词法分析器类;
import stone.词类;
import stone.带类型语法分析器类;
import stone.分析例外;
import stone.ast.语法树类;
import stone.ast.空声明类;
import chap11.环境优化器类;
import chap11.可变长度数组环境类;
import chap6.基本求值器类;
import chap6.环境类;

public class 带类型解释器类 {
    public static void main(String[] args) throws 分析例外, 类型例外 {
        类型环境类 te = new 类型环境类();
        run(new 带类型语法分析器类(),
            new 带类型原生类(te).环境(new 可变长度数组环境类()),
            te);
    }
    public static void run(基本语法分析器类 bp, 环境类 env, 类型环境类 typeEnv)
        throws 分析例外, 类型例外
    {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        while (lexer.瞄(0) != 词类.EOF) {
            语法树类 tree = bp.分析(lexer);
            if (!(tree instanceof 空声明类)) {
                ((环境优化器类.语法树优化执行类)tree).lookup(
                                        ((环境优化器类.环境执行类2)env).symbols());
                类型信息类 type
                    = ((类型检查器类.语法树类型执行类)tree).typeCheck(typeEnv);
                Object r = ((基本求值器类.语法树执行类)tree).求值(env);
                System.out.println("=> " + r + " : " + type);
            }
        }
    }
}
