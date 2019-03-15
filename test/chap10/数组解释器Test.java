package chap10;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import chap7.NestedEnv;
import chap8.Natives;
import chap9.ClassInterpreter;
import javassist.gluonj.util.UTester;
import stone.ClassParser;
import stone.分析例外;
import stone.util.解释器功用;

public class 数组解释器Test extends ClassInterpreter {

  private static final String 换行 = "\n";
  private static final String 数字数组 = "a = [2, 3, 4]";
  private static final String 数字数组取值 = "a[1]";
  private static final String 混合数组赋值 = "a[1] = \"three\"";
  private static final String 混合数组取值 = "\"a[1]: \" + a[1]";
  private static final String 多层数组 = "b = [[\"one\", 1], [\"two\", 2]]";
  private static final String 多层数组取值 = "b[1][0] + \": \" + b[1][1]";

  public static Object 求值(String 源代码) throws 分析例外 {
    return 解释器功用.求值(new ClassParser(), new Natives().environment(new NestedEnv()), 源代码);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap9.ClassEvaluator", "chap10.ArrayEvaluator",
        "chap8.NativeEvaluator", "chap7.ClosureEvaluator"))
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
