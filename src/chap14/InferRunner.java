package chap14;

import javassist.gluonj.util.Loader;
import chap8.原生求值器类;

public class InferRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(带类型解释器类.class, args, 推导函数类型类.class,
                                                 原生求值器类.class);
    }
}
