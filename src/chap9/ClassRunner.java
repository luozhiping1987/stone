package chap9;
import javassist.gluonj.util.Loader;
import chap7.闭包求值器类;
import chap8.NativeEvaluator;

public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class,
                   NativeEvaluator.class, 闭包求值器类.class);
    }
}
