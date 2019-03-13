package chap11;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap6.BasicEvaluator;
import chap6.Environment;
import chap8.Natives;
import javassist.gluonj.util.UTester;
import stone.BasicParser;
import stone.ClosureParser;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.ast.ASTree;
import stone.ast.NullStmnt;
import stone.util.文件功用;

public class 环境优化解释器Test extends EnvOptInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print \"优化后: \" + cost + \" 毫秒\"";
  private static String 斐波那契计时 = "";
  static {
    try {
      斐波那契计时 = 文件功用.读文件("测试源码/chap8/斐波那契计时.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static Object 求值(String 源代码) throws ParseException {
    return 求值(new ClosureParser(), new Natives().environment(new ResizableArrayEnv()), 源代码);
  }

  public static Object 求值(BasicParser 基本分析器, Environment 环境, String 源代码) throws ParseException {
    Lexer 词法分析器 = new Lexer(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.peek(0) != Token.EOF) {
      ASTree 树 = 基本分析器.parse(词法分析器);
      if (!(树 instanceof NullStmnt)) {
        ((EnvOptimizer.ASTreeOptEx)树).lookup(
            ((EnvOptimizer.EnvEx2)环境).symbols());
        终值 = ((BasicEvaluator.ASTreeEx) 树).eval(环境);
      }
    }
    return 终值;
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap11.EnvOptimizer", "chap8.NativeEvaluator"))
      return;
    Object 值 = 求值(斐波那契计时);
    assertTrue((值 instanceof Integer ? (Integer) 值 : -1) > 0);
    assertEquals(0, 求值(斐波那契计时 + 换行 + 打印));
  }
}
