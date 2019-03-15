package chap6;
import javassist.gluonj.util.Loader;

public class Runner {
    public static void main(String[] args) throws Throwable {
        Loader.run(基本解释器类.class, args, 基本求值器类.class);
    }
}
