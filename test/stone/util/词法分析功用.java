package stone.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import stone.Lexer;
import stone.Token;

public class 词法分析功用 {

  private 词法分析功用() {}
  
  public static Lexer 新建词法分析器(String 字符串) {
    return new Lexer(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
        字符串.getBytes()))));
  }

  public static void 词为数(String 名, Token 词) {
    assertEquals(名, 词.getText());
    assertEquals(true, 词.isNumber());
  }

  public static void 词为标识符(String 名, Token 词) {
    assertEquals(名, 词.getText());
    assertEquals(true, 词.isIdentifier());
  }
}
