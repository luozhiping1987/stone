package chap13;
import stone.FuncParser;
import stone.分析例外;
import chap11.EnvOptInterpreter;
import chap8.Natives;

public class VmInterpreter extends EnvOptInterpreter {
    public static void main(String[] args) throws 分析例外 {
        run(new FuncParser(),
            new Natives().environment(new StoneVMEnv(100000, 100000, 1000)));
    }
}
