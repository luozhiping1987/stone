package chap7;
import javassist.gluonj.util.Loader;

public class FuncRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(函数解释器类.class, args, 函数求值器类.class);
    }
}
