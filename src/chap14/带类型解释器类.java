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
                ((环境优化器类.语法树优化扩展类)tree).查找(
                                        ((环境优化器类.环境扩展类2)env).所有符号());
                类型信息类 type
                    = ((类型检查器类.语法树类型扩展类)tree).类型检查(typeEnv);
                Object r = ((基本求值器类.语法树扩展类)tree).求值(env);
                System.out.println("=> " + r + " : " + type);
            }
        }
    }
}
