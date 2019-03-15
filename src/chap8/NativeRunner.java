package chap8;
import javassist.gluonj.util.Loader;
import chap7.闭包求值器类;

public class NativeRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(原生解释器类.class, args, 原生求值器类.class,
                   闭包求值器类.class);
    }
}
