package chap14;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import chap11.EnvOptimizer;
import chap11.ResizableArrayEnv;
import chap6.BasicEvaluator;
import chap6.Environment;
import javassist.gluonj.util.UTester;
import stone.BasicParser;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.TypedParser;
import stone.ast.ASTree;
import stone.ast.NullStmnt;
import stone.util.带类型返回值;
import stone.util.文件功用;

public class 类型解释器 extends TypedInterpreter {

  private static final String 换行 = "\n";
  private static final String 打印 = "print cost + \" 毫秒 静态类型\"";
  private static final String 计时 = "t = currentTime()";
  private static final String 求值 = "fib(20)";
  private static final String 计时结束 = "cost = currentTime() - t";
  private static String 斐波那契 = "";
  static {
    try {
      斐波那契 = 文件功用.读文件("测试源码/chap7/斐波那契.txt", StandardCharsets.UTF_8);
    } catch (IOException e) {
    }
  }

  public static 带类型返回值 求值(String 源代码) throws ParseException, TypeException {
    TypeEnv te = new TypeEnv();
    return 求值(new TypedParser(),
        new TypedNatives(te).environment(new ResizableArrayEnv()), te, 源代码);
  }

  public static 带类型返回值 求值(BasicParser bp, Environment env, TypeEnv typeEnv, String 源代码)
      throws ParseException, TypeException {
    Lexer lexer = new Lexer(new StringReader(源代码));
    Object 终值 = null;
    TypeInfo 类型 = null;
    while (lexer.peek(0) != Token.EOF) {
      ASTree tree = bp.parse(lexer);
      if (!(tree instanceof NullStmnt)) {
        ((EnvOptimizer.ASTreeOptEx) tree).lookup(((EnvOptimizer.EnvEx2) env).symbols());

        类型 = ((TypeChecker.ASTreeTypeEx) tree).typeCheck(typeEnv);
        终值 = ((BasicEvaluator.ASTreeEx) tree).eval(env);
      }
    }
    return new 带类型返回值(终值, 类型);
  }

  @Test
  public void 例程() throws Throwable {
    if (UTester.runTestWith("chap14.ToJava", "chap14.InferFuncTypes", "chap8.NativeEvaluator"))
      return;
    带类型返回值 值 = 求值(斐波那契 + 换行 + 计时 + 换行 + 求值);
    assertEquals(6765, 值.返回值);
    值 = 求值(斐波那契 + 换行 + 计时 + 换行 + 求值 + 换行 + 计时结束 + 换行 + 打印);
    assertEquals(0, 值.返回值);
  }
}
