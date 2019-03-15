package chap9;
import javassist.gluonj.util.Loader;
import chap7.闭包求值器类;
import chap8.原生求值器类;

public class ClassRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(类解释器类.class, args, 类求值器类.class,
                   原生求值器类.class, 闭包求值器类.class);
    }
}
