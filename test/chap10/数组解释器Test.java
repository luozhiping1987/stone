package chap10;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chap7.嵌套环境类;
import chap8.原生类;
import chap9.类解释器类;
import javassist.gluonj.util.UTester;
import stone.分析例外;
import stone.类语法分析器类;
import stone.util.类路径常量;
import stone.util.解释器功用;

public class 数组解释器Test extends 类解释器类 {

  private static final String 换行 = "\n";
  private static final String 数字数组 = "a = [2, 3, 4]";
  private static final String 数字数组取值 = "a[1]";
  private static final String 混合数组赋值 = "a[1] = \"three\"";
  private static final String 混合数组取值 = "\"a[1]: \" + a[1]";
  private static final String 多层数组 = "b = [[\"one\", 1], [\"two\", 2]]";
  private static final String 多层数组取值 = "b[1][0] + \": \" + b[1][1]";

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new 类语法分析器类(), new 原生类().环境(new 嵌套环境类()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith(类路径常量.类求值器, 类路径常量.数组求值器, 类路径常量.原生求值器, 类路径常量.闭包求值器))
      return;
    // assertEquals("a", 求值(数字数组));
    assertEquals(3, 求值(数字数组 + 换行 + 数字数组取值));
    assertEquals("three", 求值(数字数组 + 换行 + 数字数组取值 + 换行 + 混合数组赋值));
    assertEquals("a[1]: three", 求值(数字数组 + 换行 + 数字数组取值 + 换行 + 混合数组赋值 + 换行 + 混合数组取值));
    // assertEquals("b", 求值(数字数组 + 换行 + 数字数组取值 + 换行 + 混合数组赋值 + 换行 + 混合数组取值 + 换行 + 多层数组));
    assertEquals("two: 2",
        求值(数字数组 + 换行 + 数字数组取值 + 换行 + 混合数组赋值 + 换行 + 混合数组取值 + 换行 + 多层数组 + 换行 + 多层数组取值));
  }

}
