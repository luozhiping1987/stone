package chap13;
import stone.函数语法分析器类;
import stone.分析例外;
import chap11.环境优化解释器类;
import chap8.原生类;

public class VmInterpreter extends 环境优化解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new 函数语法分析器类(),
            new 原生类().环境(new StoneVMEnv(100000, 100000, 1000)));
    }
}
