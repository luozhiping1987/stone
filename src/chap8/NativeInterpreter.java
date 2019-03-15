package chap8;
import stone.ClosureParser;
import stone.分析例外;
import chap6.基本解释器类;
import chap7.NestedEnv;

public class NativeInterpreter extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new ClosureParser(),
            new Natives().environment(new NestedEnv()));
    }
}
