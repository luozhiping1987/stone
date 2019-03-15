package chap14;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class TypedRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(带类型解释器类.class, args, 类型检查器类.class,
                                                 原生求值器类.class);
    }
}
