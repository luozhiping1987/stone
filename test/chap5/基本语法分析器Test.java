package chap5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import stone.基本语法分析器类;
import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.ast.语法树叶类;
import stone.ast.语法树类;
import stone.ast.二元表达式;
import stone.ast.While声明;
import stone.util.文件功用;
import stone.util.词法分析功用;

public class 基本语法分析器Test {
  
  @Test
  public void test分析赋值() throws 分析例外 {
    词法分析器类 词法分析器 = 词法分析功用.新建词法分析器("i0 = 1");
    基本语法分析器类 语法分析器 = new 基本语法分析器类();
    语法树类 树 = 语法分析器.分析(词法分析器);
    
    assertTrue(树 instanceof 二元表达式);
    assertEquals(3, 树.子个数());
    词法分析功用.词为标识符("i0", ((语法树叶类) 树.子(0)).词());
    词法分析功用.词为标识符("=", ((语法树叶类) 树.子(1)).词());
    词法分析功用.词为数("1", ((语法树叶类) 树.子(2)).词());
  }

  @Test
  public void test分析程序() throws 分析例外, IOException {
    String 源码 = 文件功用.读文件("测试源码/chap5/分奇偶加.txt", StandardCharsets.UTF_8);
    词法分析器类 词法分析器 = 词法分析功用.新建词法分析器(源码);

    基本语法分析器类 语法分析器 = new 基本语法分析器类();
    List<语法树类> 所有语法树 = new ArrayList<>();
    while (词法分析器.瞄(0) != 词类.EOF) {
      所有语法树.add(语法分析器.分析(词法分析器));
    }

    assertEquals(5, 所有语法树.size());
    assertTrue(所有语法树.get(0) instanceof 二元表达式);
    assertTrue(所有语法树.get(1) instanceof 二元表达式);
    assertTrue(所有语法树.get(2) instanceof 二元表达式);
    assertTrue(所有语法树.get(3) instanceof While声明);
    assertTrue(所有语法树.get(4) instanceof 二元表达式);
  }
}
