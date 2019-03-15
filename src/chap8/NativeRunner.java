package chap8;
import javassist.gluonj.util.Loader;
import chap7.闭包求值器类;

public class NativeRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(NativeInterpreter.class, args, NativeEvaluator.class,
                   闭包求值器类.class);
    }
}
