package stone.util;

import java.io.StringReader;

import chap11.EnvOptimizer;
import chap14.TypeChecker;
import chap14.TypeEnv;
import chap14.TypeException;
import chap14.TypeInfo;
import chap6.基本求值器类;
import chap6.Environment;
import stone.基本语法分析器类;
import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.ast.语法树类;
import stone.ast.NullStmnt;

public class 解释器功用 {
  public static Object 求值(基本语法分析器类 基本分析器, Environment 环境, String 源代码) throws 分析例外 {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof NullStmnt)) {
        终值 = ((基本求值器类.ASTreeEx) 树).eval(环境);
      }
    }
    return 终值;
  }

  public static Object 环境优化求值(基本语法分析器类 基本分析器, Environment 环境, String 源代码) throws 分析例外 {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof NullStmnt)) {
        ((EnvOptimizer.ASTreeOptEx) 树).lookup(((EnvOptimizer.EnvEx2) 环境).symbols());
        终值 = ((基本求值器类.ASTreeEx) 树).eval(环境);
      }
    }
    return 终值;
  }

  public static 带类型返回值 带类型求值(基本语法分析器类 基本分析器, Environment 环境, TypeEnv 类型环境, String 源代码)
      throws 分析例外, TypeException {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    TypeInfo 类型 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof NullStmnt)) {
        ((EnvOptimizer.ASTreeOptEx) 树).lookup(((EnvOptimizer.EnvEx2) 环境).symbols());

        类型 = ((TypeChecker.ASTreeTypeEx) 树).typeCheck(类型环境);
        终值 = ((基本求值器类.ASTreeEx) 树).eval(环境);
      }
    }
    return new 带类型返回值(终值, 类型);
  }

}
