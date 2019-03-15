package chap7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import javassist.gluonj.util.UTester;
import stone.FuncParser;
import stone.分析例外;
import stone.util.文件功用;
import stone.util.解释器功用;

public class 函数解释器Test extends FuncInterpreter {

  private static final String 换行 = "\n";
  private static final String 求值 = "fib(20)";
  private static String 斐波那契函数 = "";
  static {
    try {
      斐波那契函数 = 文件功用.读文件("测试源码/chap7/斐波那契.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new FuncParser(), new NestedEnv(), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap7.FuncEvaluator"))
      return;
    assertEquals("fib", 求值(斐波那契函数));
    assertEquals(6765, 求值(斐波那契函数 + 换行 + 求值));
  }

}
