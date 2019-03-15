package chap7;
import stone.函数语法分析器类;
import stone.分析例外;
import chap6.基本解释器类;

public class 函数解释器类 extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 函数语法分析器类(), new 嵌套环境类());
    }
}
