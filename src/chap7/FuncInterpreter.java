package chap7;
import stone.FuncParser;
import stone.分析例外;
import chap6.BasicInterpreter;

public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws 分析例外 {
        run(new FuncParser(), new NestedEnv());
    }
}
