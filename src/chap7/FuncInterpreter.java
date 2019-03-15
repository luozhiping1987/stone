package chap7;
import stone.FuncParser;
import stone.分析例外;
import chap6.基本解释器类;

public class FuncInterpreter extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new FuncParser(), new NestedEnv());
    }
}
