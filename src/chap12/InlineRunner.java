package chap12;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class InlineRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ObjOptInterpreter.class, args, InlineCache.class,
                                                  原生求值器类.class);
    }
}
