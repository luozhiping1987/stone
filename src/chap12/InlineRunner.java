package chap12;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class InlineRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(对象优化解释器类.class, args, 内联缓存类.class,
                                                  原生求值器类.class);
    }
}
