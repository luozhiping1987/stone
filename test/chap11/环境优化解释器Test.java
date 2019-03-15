package chap11;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap8.原生类;
import javassist.gluonj.util.UTester;
import stone.分析例外;
import stone.闭包语法分析器类;
import stone.util.文件功用;
import stone.util.类路径常量;
import stone.util.解释器功用;

public class 环境优化解释器Test extends EnvOptInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒 环境优化\"";
  private static String 斐波那契计时 = "";
  static {
    try {
      斐波那契计时 = 文件功用.读文件("测试源码/chap8/斐波那契计时.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.环境优化求值(new 闭包语法分析器类(), new 原生类().环境(new ResizableArrayEnv()), 源代码);
  }


  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap11.EnvOptimizer", 类路径常量.原生求值器))
      return;
    Object 值 = 求值(斐波那契计时);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时 + 换行 + 打印));
  }
}
