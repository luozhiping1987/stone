package chap9;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap7.嵌套环境类;
import chap8.原生类;
import javassist.gluonj.util.UTester;
import stone.类语法分析器类;
import stone.分析例外;
import stone.util.文件功用;
import stone.util.类路径常量;
import stone.util.解释器功用;

public class 面向对象解释器Test extends 类解释器类 {

  private static String 位置类 = "";
  static {
    try {
      位置类 = 文件功用.读文件("测试源码/chap9/位置类.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new 类语法分析器类(), new 原生类().环境(new 嵌套环境类()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith(类路径常量.类求值器, 类路径常量.原生求值器, 类路径常量.闭包求值器))
      return;
    assertEquals(14, 求值(位置类));
  }
}
