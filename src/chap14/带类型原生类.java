package chap14;
import chap11.环境优化器类.环境扩展类2;
import chap6.环境类;
import chap8.原生类;

public class 带类型原生类 extends 原生类 {
  private static final String 方法名_当前时刻 = "当前时刻";
  private static final String 方法名_打印 = "打印";
    protected 类型环境类 typeEnv;
    public 带类型原生类(类型环境类 te) { typeEnv = te; }
    protected void append(环境类 env, String name, Class<?> clazz,
                          String methodName, 类型信息类 type, Class<?> ... params)
    {
        append(env, name, clazz, methodName, params);
        int index = ((环境扩展类2)env).所有符号().find(name);
        typeEnv.put(0, index, type);
    }
    @Override
    protected void appendNatives(环境类 env) {
        append(env, 方法名_打印, chap14.java.print.class, "m",
               类型信息类.function(类型信息类.INT, 类型信息类.ANY),
               Object.class);
        append(env, "read", chap14.java.read.class, "m",
                类型信息类.function(类型信息类.STRING));
        append(env, "length", chap14.java.length.class, "m",
               类型信息类.function(类型信息类.INT, 类型信息类.STRING),
               String.class);
        append(env, "toInt", chap14.java.toInt.class, "m",
               类型信息类.function(类型信息类.INT, 类型信息类.ANY),
               Object.class);
        append(env, 方法名_当前时刻, chap14.java.currentTime.class, "m",
               类型信息类.function(类型信息类.INT));
    }
}
