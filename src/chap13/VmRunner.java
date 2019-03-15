package chap13;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class VmRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(VmInterpreter.class, args, VmEvaluator.class,
                                              原生求值器类.class);
    }
}
