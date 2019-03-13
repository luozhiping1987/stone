package stone.util;

import java.io.StringReader;

import chap6.BasicEvaluator;
import chap6.Environment;
import stone.BasicParser;
import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.ast.ASTree;
import stone.ast.NullStmnt;

public class 解释器功用 {
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
}
