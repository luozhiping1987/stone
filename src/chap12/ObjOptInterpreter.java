package chap12;
import stone.类语法分析器类;
import stone.分析例外;
import chap11.环境优化解释器类;
import chap11.可变长度数组环境类;
import chap8.原生类;

public class ObjOptInterpreter extends 环境优化解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 类语法分析器类(),
            new 原生类().环境(new 可变长度数组环境类()));
    }
}
