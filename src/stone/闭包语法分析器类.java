package stone;
import static stone.Parser.rule;
import stone.ast.Fun;

public class 闭包语法分析器类 extends 函数语法分析器类 {
    public 闭包语法分析器类() {
        primary.insertChoice(rule(Fun.class)
                                 .sep("fun").ast(paramList).ast(block));
    }
}
