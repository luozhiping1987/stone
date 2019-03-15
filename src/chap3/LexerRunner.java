package chap3;
import stone.*;

public class LexerRunner {
    public static void main(String[] args) throws 分析例外 {
        词法分析器类 l = new 词法分析器类(new CodeDialog());
        for (词类 t; (t = l.读()) != 词类.EOF; )
            System.out.println("=> " + t.取文本());
    }
}
