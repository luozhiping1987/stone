package chap3;

import org.junit.Test;

import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.util.词法分析功用;

public class 词法分析器Test {

  @Test
  public void test读() throws 分析例外 {
    词法分析器类 词法分析器 = 词法分析功用.新建词法分析器("i0 = 1");
    词类 词1 = 词法分析器.读();
    词类 词2 = 词法分析器.读();
    词类 词3 = 词法分析器.读();

    词法分析功用.词为标识符("i0", 词1);
    词法分析功用.词为标识符("=", 词2);
    词法分析功用.词为数("1", 词3);
  }
}
