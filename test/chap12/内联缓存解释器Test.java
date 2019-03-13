package chap12;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap11.ResizableArrayEnv;
import chap8.Natives;
import javassist.gluonj.util.UTester;
import stone.ClassParser;
import stone.ParseException;
import stone.util.文件功用;
import stone.util.解释器功用;

public class 内联缓存解释器Test extends ObjOptInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒\"";
  private static String 斐波那契计时_内联缓存 = "";
  private static String 斐波那契计时_无内联缓存 = "";
  static {
    try {
      斐波那契计时_内联缓存 = 文件功用.读文件("测试源码/chap12/斐波那契计时_内联缓存.txt", StandardCharsets.UTF_8);
      斐波那契计时_无内联缓存 = 文件功用.读文件("测试源码/chap12/斐波那契计时_无内联缓存.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws ParseException {
    return 解释器功用.求值(new ClassParser(), new Natives().environment(new ResizableArrayEnv()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap12.InlineCache", "chap8.NativeEvaluator"))
      return;
    Object 值 = 求值(斐波那契计时_无内联缓存);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时_无内联缓存 + 换行 + 打印 + " + \" 无内联缓存\""));

    值 = 求值(斐波那契计时_内联缓存);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时_内联缓存 + 换行 + 打印 + " + \" 内联缓存\""));
  }

}
