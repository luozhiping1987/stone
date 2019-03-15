package chap13;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class VmRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(虚拟机解释器类.class, args, 虚拟机求值器类.class,
                                              原生求值器类.class);
    }
}
