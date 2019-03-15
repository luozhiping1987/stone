package chap5;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import stone.BasicParser;
import stone.词法分析器类;
import stone.ParseException;
import stone.词类;
import stone.ast.ASTLeaf;
import stone.ast.ASTree;
import stone.ast.BinaryExpr;
import stone.ast.WhileStmnt;
import stone.util.文件功用;
import stone.util.词法分析功用;

public class 基本语法分析器Test {
  
  @Test
  public void test分析赋值() throws ParseException {
    词法分析器类 l = 词法分析功用.新建词法分析器("i0 = 1");
    BasicParser bp = new BasicParser();
    ASTree ast = bp.parse(l);
    
    assertTrue(ast instanceof BinaryExpr);
    assertEquals(3, ast.numChildren());
    词法分析功用.词为标识符("i0", ((ASTLeaf) ast.child(0)).token());
    词法分析功用.词为标识符("=", ((ASTLeaf) ast.child(1)).token());
    词法分析功用.词为数("1", ((ASTLeaf) ast.child(2)).token());
  }

  @Test
  public void test分析程序() throws ParseException, IOException {
    String 源码 = 文件功用.读文件("测试源码/chap5/分奇偶加.txt", StandardCharsets.UTF_8);
    词法分析器类 l = 词法分析功用.新建词法分析器(源码);

    BasicParser 语法分析器 = new BasicParser();
    List<ASTree> 所有语法树 = new ArrayList<>();
    while (l.peek(0) != 词类.EOF) {
      所有语法树.add(语法分析器.parse(l));
    }

    assertEquals(5, 所有语法树.size());
    assertTrue(所有语法树.get(0) instanceof BinaryExpr);
    assertTrue(所有语法树.get(1) instanceof BinaryExpr);
    assertTrue(所有语法树.get(2) instanceof BinaryExpr);
    assertTrue(所有语法树.get(3) instanceof WhileStmnt);
    assertTrue(所有语法树.get(4) instanceof BinaryExpr);
  }
}
