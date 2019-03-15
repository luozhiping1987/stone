package chap13;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap8.Natives;
import javassist.gluonj.util.UTester;
import stone.函数语法分析器类;
import stone.分析例外;
import stone.util.文件功用;
import stone.util.解释器功用;

public class 虚拟机解释器Test extends VmInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒 虚拟机\"";
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

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.环境优化求值(new 函数语法分析器类(),
        new Natives().environment(new StoneVMEnv(100000, 100000, 1000)), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap13.VmEvaluator", "chap8.NativeEvaluator"))
      return;
    assertEquals(6765, 求值(斐波那契 + 换行 + 计时 + 换行 + 求值));
    assertEquals(0, 求值(斐波那契 + 换行 + 计时 + 换行 + 求值 + 换行 + 计时结束 + 换行 + 打印));
  }

}
