package chap9;
import stone.类语法分析器类;
import stone.分析例外;
import chap6.基本解释器类;
import chap7.嵌套环境类;
import chap8.原生类;

public class 类解释器类 extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 类语法分析器类(), new 原生类().环境(new 嵌套环境类())); 
    }
}
