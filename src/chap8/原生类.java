package chap8;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;

import chap6.环境类;
import stone.StoneException;

public class 原生类 {
  private static final String 方法名_当前时刻 = "当前时刻";
  private static final String 方法名_打印 = "打印";
    public 环境类 环境(环境类 env) {
        appendNatives(env);
        return env;
    }
    protected void appendNatives(环境类 env) {
        append(env, 方法名_打印, 原生类.class, "print", Object.class);
        append(env, "read", 原生类.class, "read");
        append(env, "length", 原生类.class, "length", String.class);
        append(env, "toInt", 原生类.class, "toInt", Object.class);
        append(env, 方法名_当前时刻, 原生类.class, "currentTime");
    }
    protected void append(环境类 env, String name, Class<?> clazz,
                          String 内部方法名, Class<?> ... params) {
        Method m;
        try {
            m = clazz.getMethod(内部方法名, params);
        } catch (Exception e) {
            throw new StoneException("cannot find a native function: "
                                     + 内部方法名);
        }
        env.put(name, new NativeFunction(内部方法名, m));
    }

    // native methods
    public static int print(Object obj) {
        System.out.println(obj.toString());
        return 0;
    }
    public static String read() {
        return JOptionPane.showInputDialog(null);
    }
    public static int length(String s) { return s.length(); }
    public static int toInt(Object value) {
        if (value instanceof String)
            return Integer.parseInt((String)value);
        else if (value instanceof Integer)
            return ((Integer)value).intValue();
        else
            throw new NumberFormatException(value.toString());
    }
    private static long startTime = System.currentTimeMillis();
    public static int currentTime() {
        return (int)(System.currentTimeMillis() - startTime);
    }
}
