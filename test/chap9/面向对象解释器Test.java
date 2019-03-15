package chap9;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap7.嵌套环境类;
import chap8.Natives;
import javassist.gluonj.util.UTester;
import stone.ClassParser;
import stone.分析例外;
import stone.util.文件功用;
import stone.util.解释器功用;

public class 面向对象解释器Test extends ClassInterpreter {

  private static String 位置类 = "";
  static {
    try {
      位置类 = 文件功用.读文件("测试源码/chap9/位置类.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new ClassParser(), new Natives().environment(new 嵌套环境类()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap9.ClassEvaluator", "chap8.NativeEvaluator", "chap7.闭包求值器类"))
      return;
    assertEquals(14, 求值(位置类));
  }
}
