package chap7;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;
import chap6.环境类;

public class Function {
    protected ParameterList parameters;
    protected BlockStmnt body;
    protected 环境类 env;
    public Function(ParameterList parameters, BlockStmnt body, 环境类 env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }
    public ParameterList parameters() { return parameters; }
    public BlockStmnt body() { return body; }
    public 环境类 makeEnv() { return new 嵌套环境类(env); }
    @Override public String toString() { return "<fun:" + hashCode() + ">"; }
}
