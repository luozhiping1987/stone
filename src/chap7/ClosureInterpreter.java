package chap7;
import stone.ClosureParser;
import stone.分析例外;
import chap6.BasicInterpreter;

public class ClosureInterpreter extends BasicInterpreter{
    public static void main(String[] args) throws 分析例外 {
        run(new ClosureParser(), new NestedEnv());
    }
}
