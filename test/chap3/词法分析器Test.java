package chap3;

import org.junit.Test;

import stone.Lexer;
import stone.ParseException;
import stone.Token;
import stone.util.词法分析功用;

public class 词法分析器Test {

  @Test
  public void test读() throws ParseException {
    Lexer 词法分析器 = 词法分析功用.新建词法分析器("i0 = 1");
    Token 词1 = 词法分析器.read();
    Token 词2 = 词法分析器.read();
    Token 词3 = 词法分析器.read();

    词法分析功用.词为标识符("i0", 词1);
    词法分析功用.词为标识符("=", 词2);
    词法分析功用.词为数("1", 词3);
  }
}
