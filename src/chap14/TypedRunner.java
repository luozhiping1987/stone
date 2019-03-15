package chap14;
import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class TypedRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(TypedInterpreter.class, args, TypeChecker.class,
                                                 原生求值器类.class);
    }
}
