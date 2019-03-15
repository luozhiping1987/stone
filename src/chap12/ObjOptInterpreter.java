package chap12;
import stone.类语法分析器类;
import stone.分析例外;
import chap11.EnvOptInterpreter;
import chap11.ResizableArrayEnv;
import chap8.原生类;

public class ObjOptInterpreter extends EnvOptInterpreter {
    public static void main(String[] args) throws 分析例外 {
        run(new 类语法分析器类(),
            new 原生类().环境(new ResizableArrayEnv()));
    }
}
