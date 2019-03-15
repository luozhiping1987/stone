package chap9;
import stone.ClassParser;
import stone.分析例外;
import chap6.基本解释器类;
import chap7.NestedEnv;
import chap8.Natives;

public class ClassInterpreter extends 基本解释器类 {
    public static void main(String[] args) throws 分析例外 {
        run(new ClassParser(), new Natives().environment(new NestedEnv())); 
    }
}
