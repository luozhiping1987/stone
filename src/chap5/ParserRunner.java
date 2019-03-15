package chap5;
import stone.ast.ASTree;
import stone.*;

public class ParserRunner {
    public static void main(String[] args) throws ParseException {
        词法分析器类 l = new 词法分析器类(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != 词类.EOF) {
            ASTree ast = bp.parse(l);
            System.out.println("=> " + ast.toString());
        }
    }
}
