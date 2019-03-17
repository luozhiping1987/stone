package stone;
import static stone.Parser.rule;

import stone.ast.DefStmnt;
import stone.ast.TypeTag;
import stone.ast.VarStmnt;

public class 带类型语法分析器类 extends 函数语法分析器类 {
    Parser typeTag = rule(TypeTag.class).sep(":").identifier(reserved);
    Parser variable = rule(VarStmnt.class)
                          .sep("var").identifier(reserved).maybe(typeTag)
                          .sep("=").ast(expr);
    public 带类型语法分析器类() {
        reserved.add(":");
        param.maybe(typeTag);
        def.reset().sep(DefStmnt.关键字).identifier(reserved).ast(paramList)
                   .maybe(typeTag).ast(block);
        statement.insertChoice(variable);
    }
}
