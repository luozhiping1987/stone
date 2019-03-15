package chap8;
import stone.ClosureParser;
import stone.分析例外;
import chap6.BasicInterpreter;
import chap7.NestedEnv;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws 分析例外 {
        run(new ClosureParser(),
            new Natives().environment(new NestedEnv()));
    }
}
