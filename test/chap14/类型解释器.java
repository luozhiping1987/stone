package chap14;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap11.ResizableArrayEnv;
import javassist.gluonj.util.UTester;
import stone.ParseException;
import stone.TypedParser;
import stone.util.带类型返回值;
import stone.util.文件功用;
import stone.util.解释器功用;

public class 类型解释器 extends TypedInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒 静态类型\"";
  private static final String 计时 = "t = currentTime()";
  private static final String 求值 = "fib(20)";
  private static final String 计时结束 = "cost = currentTime() - t";
  private static String 斐波那契 = "";
  static {
    try {
      斐波那契 = 文件功用.读文件("测试源码/chap7/斐波那契.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static 带类型返回值 求值(String 源代码) throws ParseException, TypeException {
    TypeEnv te = new TypeEnv();
    return 解释器功用.带类型求值(new TypedParser(),
        new TypedNatives(te).environment(new ResizableArrayEnv()), te, 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap14.ToJava", "chap14.InferFuncTypes", "chap8.NativeEvaluator"))
      return;
    带类型返回值 值 = 求值(斐波那契 + 换行 + 计时 + 换行 + 求值);
    assertEquals(6765, 值.返回值);
    值 = 求值(斐波那契 + 换行 + 计时 + 换行 + 求值 + 换行 + 计时结束 + 换行 + 打印);
    assertEquals(0, 值.返回值);
  }
}
