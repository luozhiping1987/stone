package chap13;
import stone.ast.BlockStmnt;
import stone.ast.ParameterList;
import chap6.环境类;
import chap7.Function;

public class VmFunction extends Function {
    protected int entry;
    public VmFunction(ParameterList parameters, BlockStmnt body,
                      环境类 env, int entry)
    {
        super(parameters, body, env);
        this.entry = entry;
    }
    public int entry() { return entry; }
}
