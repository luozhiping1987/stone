package chap11;
import chap8.原生求值器类;
import javassist.gluonj.util.Loader;

public class EnvOptRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(环境优化解释器类.class, args, 环境优化器类.class,
                                                  原生求值器类.class);
    }
}
