package chap5;
import stone.ast.语法树类;
import stone.*;

public class ParserRunner {
    public static void main(String[] args) throws 分析例外 {
        词法分析器类 l = new 词法分析器类(new CodeDialog());
        基本语法分析器类 bp = new 基本语法分析器类();
        while (l.瞄(0) != 词类.EOF) {
            语法树类 ast = bp.分析(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
