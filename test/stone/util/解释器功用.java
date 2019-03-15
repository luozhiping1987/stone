package stone.util;

import java.io.StringReader;

import chap11.环境优化器类;
import chap14.类型检查器类;
import chap14.类型环境类;
import chap14.类型例外;
import chap14.类型信息类;
import chap6.基本求值器类;
import chap6.环境类;
import stone.基本语法分析器类;
import stone.词法分析器类;
import stone.分析例外;
import stone.词类;
import stone.ast.语法树类;
import stone.ast.空声明类;

public class 解释器功用 {
  public static Object 求值(基本语法分析器类 基本分析器, 环境类 环境, String 源代码) throws 分析例外 {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof 空声明类)) {
        终值 = ((基本求值器类.语法树扩展类) 树).求值(环境);
      }
    }
    return 终值;
  }

  public static Object 环境优化求值(基本语法分析器类 基本分析器, 环境类 环境, String 源代码) throws 分析例外 {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof 空声明类)) {
        ((环境优化器类.语法树优化扩展类) 树).查找(((环境优化器类.环境扩展类2) 环境).所有符号());
        终值 = ((基本求值器类.语法树扩展类) 树).求值(环境);
      }
    }
    return 终值;
  }

  public static 带类型返回值 带类型求值(基本语法分析器类 基本分析器, 环境类 环境, 类型环境类 类型环境, String 源代码)
      throws 分析例外, 类型例外 {
    词法分析器类 词法分析器 = new 词法分析器类(new StringReader(源代码));
    Object 终值 = null;
    类型信息类 类型 = null;
    while (词法分析器.瞄(0) != 词类.EOF) {
      语法树类 树 = 基本分析器.分析(词法分析器);
      if (!(树 instanceof 空声明类)) {
        ((环境优化器类.语法树优化扩展类) 树).查找(((环境优化器类.环境扩展类2) 环境).所有符号());

        类型 = ((类型检查器类.语法树类型扩展类) 树).类型检查(类型环境);
        终值 = ((基本求值器类.语法树扩展类) 树).求值(环境);
      }
    }
    return new 带类型返回值(终值, 类型);
  }

}
