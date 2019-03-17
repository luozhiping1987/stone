package chap7;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import javassist.gluonj.util.UTester;
import stone.分析例外;
import stone.闭包语法分析器类;
import stone.util.文件功用;
import stone.util.类路径常量;
import stone.util.解释器功用;

public class 闭包解释器Test extends 闭包解释器类 {

  private static final String 换行 = "\n";
  private static final String 求值1 = "c1()";
  private static final String 求值2 = "c2()";
  private static final String 闭包累加函数 = "inc = 函数 (x) { x + 1 }";
  private static final String 求值 = "inc(3)";

  private static String 嵌套闭包累加函数 = "";
  static {
    try {
      嵌套闭包累加函数 = 文件功用.读文件("测试源码/chap7/嵌套闭包累加.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new 闭包语法分析器类(), new 嵌套环境类(), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith(类路径常量.闭包求值器))
      return;
    // assertEquals("counter", 求值(闭包累加函数));
    assertEquals(1, 求值(嵌套闭包累加函数 + 换行 + 求值1));
    assertEquals(2, 求值(嵌套闭包累加函数 + 换行 + 求值1 + 换行 + 求值1));
    assertEquals(1, 求值(嵌套闭包累加函数 + 换行 + 求值1 + 换行 + 求值1 + 换行 + 求值2));

    assertEquals(4, 求值(闭包累加函数 + 换行 + 求值));
  }
}
