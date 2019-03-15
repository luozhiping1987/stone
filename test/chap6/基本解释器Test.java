package chap6;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javassist.gluonj.util.UTester;
import stone.分析例外;
import stone.基本语法分析器类;
import stone.util.类路径常量;
import stone.util.解释器功用;

// 测试方式参考: https://github.com/chibash/gluonj/blob/fc453d9a187e60dfdaa1084a8974b8a4ba072aae/src/javassist/gluonj/util/UTester.java#L36
public class 基本解释器Test extends 基本解释器类 {

  private static final String 换行 = "\n";
  private static final String 初始化和 = "sum = 0";
  private static final String 初始化计数器 = "i = 1";
  private static final String 循环累加 =
    "while i < 10 {\n" +
    "  sum = sum + i\n" +
    "  i = i + 1\n" +
    "}";
  private static final String 求值 = "sum";

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new 基本语法分析器类(), new 基本环境类(), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith(类路径常量.基本求值器))
      return;
    assertEquals(0, 求值(初始化和));
    assertEquals(1, 求值(初始化和 + 换行 + 初始化计数器));
    assertEquals(10, 求值(初始化和 + 换行 + 初始化计数器 + 换行 + 循环累加));
    assertEquals(45, 求值(初始化和 + 换行 + 初始化计数器 + 换行 + 循环累加 + 换行 + 求值));
  }

}
