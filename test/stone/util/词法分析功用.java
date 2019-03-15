package stone.util;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import stone.词法分析器类;
import stone.词类;

public class 词法分析功用 {

  private 词法分析功用() {}
  
  public static 词法分析器类 新建词法分析器(String 字符串) {
    return new 词法分析器类(new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
        字符串.getBytes()))));
  }

  public static void 词为数(String 名, 词类 词) {
    assertEquals(名, 词.取文本());
    assertEquals(true, 词.为数());
  }

  public static void 词为标识符(String 名, 词类 词) {
    assertEquals(名, 词.取文本());
    assertEquals(true, 词.为标识符());
  }
}
