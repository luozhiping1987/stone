package chapB;
import java.util.Arrays;
import java.util.HashMap;
import stone.*;
import stone.ast.*;

public class OpPrecedenceParser {
    private 词法分析器类 lexer;
    protected HashMap<String,Precedence> operators;

    public static class Precedence {
        int value;
        boolean leftAssoc; // left associative
        public Precedence(int v, boolean a) {
            value = v; leftAssoc = a;
        }
    }
    public OpPrecedenceParser(词法分析器类 p) {
        lexer = p;
        operators = new HashMap<String,Precedence>();
        operators.put("<", new Precedence(1, true));
        operators.put(">", new Precedence(1, true));
        operators.put("+", new Precedence(2, true));
        operators.put("-", new Precedence(2, true));
        operators.put("*", new Precedence(3, true));
        operators.put("/", new Precedence(3, true));
        operators.put("^", new Precedence(4, false));
    }
    public 语法树类 expression() throws 分析例外 {
        语法树类 right = factor();
        Precedence next;
        while ((next = nextOperator()) != null)
            right = doShift(right, next.value);

        return right;
    }
    private 语法树类 doShift(语法树类 left, int prec) throws 分析例外 {
        语法树叶类 op = new 语法树叶类(lexer.读());
        语法树类 right = factor();
        Precedence next;
        while ((next = nextOperator()) != null && rightIsExpr(prec, next))
            right = doShift(right, next.value);

        return new 二元表达式类(Arrays.asList(left, op, right));
    }
    private Precedence nextOperator() throws 分析例外 {
        词类 t = lexer.瞄(0);
        if (t.为标识符())
            return operators.get(t.取文本());
        else
            return null;
    }
    private static boolean rightIsExpr(int prec, Precedence nextPrec) {
        if (nextPrec.leftAssoc)
            return prec < nextPrec.value;
        else
            return prec <= nextPrec.value;
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
            if (t.为数()) {
                NumberLiteral n = new NumberLiteral(t);
                return n;
            }
            else
                throw new 分析例外(t);
        }
    }
    void token(String name) throws 分析例外 {
        词类 t = lexer.读();
        if (!(t.为标识符() && name.equals(t.取文本())))
            throw new 分析例外(t);
    }
    boolean isToken(String name) throws 分析例外 {
        词类 t = lexer.瞄(0);
        return t.为标识符() && name.equals(t.取文本());
    }
    public static void main(String[] args) throws 分析例外 {
        词法分析器类 lexer = new 词法分析器类(new CodeDialog());
        OpPrecedenceParser p = new OpPrecedenceParser(lexer);
        语法树类 t = p.expression();
        System.out.println("=> " + t);
    }
}
