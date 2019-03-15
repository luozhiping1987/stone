package chap11;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;
import chap6.环境类;
import chap7.Function;

public class OptFunction extends Function {
    protected int size;
    public OptFunction(ParameterList parameters, BlockStmnt body,
                       环境类 env, int memorySize)
    {
        super(parameters, body, env);
        size = memorySize;
    }
    @Override public 环境类 makeEnv() { return new ArrayEnv(size, env); }
}
