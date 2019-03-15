package chap8;
import stone.闭包语法分析器类;
import stone.分析例外;
import chap6.基本解释器类;
import chap7.嵌套环境类;

public class 原生解释器类 extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 闭包语法分析器类(),
            new 原生类().环境(new 嵌套环境类()));
    }
}
