package chap7;
import javassist.gluonj.util.Loader;

public class ClosureRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(闭包解释器类.class, args, 闭包求值器类.class);
    }
}
