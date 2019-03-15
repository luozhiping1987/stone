package chapB;
import java.util.Arrays;
import stone.*;
import stone.ast.*;

public class ExprParser {
    private 词法分析器类 lexer;

    public ExprParser(词法分析器类 p) {
        lexer = p;
    }
    public 语法树类 expression() throws 分析例外 {
        语法树类 left = term();
        while (isToken("+") || isToken("-")) {
            语法树叶类 op = new 语法树叶类(lexer.读());
            语法树类 right = term();
            left = new 二元表达式(Arrays.asList(left, op, right));
        }
        return left;
    }
    public 语法树类 term() throws 分析例外 {
        语法树类 left = factor();
        while (isToken("*") || isToken("/")) {
            语法树叶类 op = new 语法树叶类(lexer.读());
            语法树类 right = factor();
            left = new 二元表达式(Arrays.asList(left, op, right));
        }
        return left;
    }
    public 语法树类 factor() throws 分析例外 {
        if (isToken("(")) {
            token("(");
            语法树类 e = expression();
            token(")");
            return e;
        }
        else {
            词类 t = lexer.读();
            if (t.isNumber()) {
                NumberLiteral n = new NumberLiteral(t);
                return n;
            }
            else
                throw new 分析例外(t);
        }
    }
    void token(String name) throws 分析例外 {
        词类 t = lexer.读();
        if (!(t.isIdentifier() && name.equals(t.getText())))
            throw new 分析例外(t);
    }
    boolean isToken(String name) throws 分析例外 {
        词类 t = lexer.瞄(0);
        return t.isIdentifier() && name.equals(t.getText());
    }

    public static void main(String[] args) throws 分析例外 {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        ExprParser p = new ExprParser(lexer);
        语法树类 t = p.expression();
        System.out.println("=> " + t);
    }
}