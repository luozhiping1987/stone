package chap12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap11.ResizableArrayEnv;
import chap8.原生类;
import javassist.gluonj.util.UTester;
import stone.ClassParser;
import stone.分析例外;
import stone.util.文件功用;
import stone.util.类路径常量;
import stone.util.解释器功用;

//耐心: 本机测试耗时15秒.
public class 变量优化解释器Test extends ObjOptInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒\"";
  private static String 斐波那契计时_this = "";
  private static String 斐波那契计时_无this = "";
  static {
    try {
      斐波那契计时_this = 文件功用.读文件("测试源码/chap12/斐波那契计时_this.txt", StandardCharsets.UTF_8);
      斐波那契计时_无this = 文件功用.读文件("测试源码/chap12/斐波那契计时_无this.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }


  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new ClassParser(), new 原生类().环境(new ResizableArrayEnv()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap12.ObjOptimizer", 类路径常量.原生求值器))
      return;

    // TODO: 为确认正确性, 只需fib(15)
    Object 值 = 求值(斐波那契计时_无this);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时_无this + 换行 + 打印 + " + \" 变量优化 无this\""));

    值 = 求值(斐波那契计时_this);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时_this + 换行 + 打印 + " + \" 变量优化 this\""));
  }

}
