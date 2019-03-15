package chap9;
import stone.ClassParser;
import stone.分析例外;
import chap6.基本解释器类;
import chap7.嵌套环境类;
import chap8.原生类;

public class ClassInterpreter extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new ClassParser(), new 原生类().环境(new 嵌套环境类())); 
    }
}
