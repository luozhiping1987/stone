package chap6;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;

import org.junit.Test;

import javassist.gluonj.util.UTester;
import stone.BasicParser;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.ast.ASTree;
import stone.ast.NullStmnt;

// 测试方式参考: https://github.com/chibash/gluonj/blob/fc453d9a187e60dfdaa1084a8974b8a4ba072aae/src/javassist/gluonj/util/UTester.java#L36
public class 基本解释器Test extends BasicInterpreter {

  private static final String 换行 = "\n";
  private static final String 初始化和 = "sum = 0";
  private static final String 初始化计数器 = "i = 1";
  private static final String 循环累加 =
    "while i < 10 {\n" +
    "  sum = sum + i\n" +
    "  i = i + 1\n" +
    "}";
  private static final String 求值 = "sum";

  public static Object 求值(String 源代码) throws ParseException {
    return 求值(new BasicParser(), new BasicEnv(), 源代码);
  }

  public static Object 求值(BasicParser 基本分析器, Environment 环境, String 源代码) throws ParseException {
    Lexer 词法分析器 = new Lexer(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.peek(0) != Token.EOF) {
      ASTree 树 = 基本分析器.parse(词法分析器);
      if (!(树 instanceof NullStmnt)) {
        终值 = ((BasicEvaluator.ASTreeEx) 树).eval(环境);
      }
    }
    return 终值;
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap6.BasicEvaluator"))
      return;
    assertEquals(0, 求值(初始化和));
    assertEquals(1, 求值(初始化和 + 换行 + 初始化计数器));
    assertEquals(10, 求值(初始化和 + 换行 + 初始化计数器 + 换行 + 循环累加));
    assertEquals(45, 求值(初始化和 + 换行 + 初始化计数器 + 换行 + 循环累加 + 换行 + 求值));
  }

}
