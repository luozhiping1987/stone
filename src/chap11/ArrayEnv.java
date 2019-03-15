package chap11;
import stone.StoneException;
import chap11.环境优化器类.环境扩展类2;
import chap6.环境类;

public class ArrayEnv implements 环境类 {
    protected Object[] values;
    protected 环境类 outer;
    public ArrayEnv(int size, 环境类 out) {
        values = new Object[size];
        outer = out;
    }
    public Symbols 所有符号() { throw new StoneException("no symbols"); }
    public Object get(int nest, int index) {
        if (nest == 0)
            return values[index];
        else if (outer == null)
            return null;
        else
            return ((环境扩展类2)outer).get(nest - 1, index);
    }
    public void put(int nest, int index, Object value) {
        if (nest == 0)
            values[index] = value;
        else if (outer == null)
            throw new StoneException("no outer environment");
        else
            ((环境扩展类2)outer).put(nest - 1, index, value);
    }
    public Object get(String name) { error(name); return null; }
    public void put(String name, Object value) { error(name); }
    public void putNew(String name, Object value) { error(name); }
    public 环境类 where(String name) { error(name); return null; }
    public void setOuter(环境类 e) { outer = e; }
    private void error(String name) {
        throw new StoneException("cannot access by name: " + name);
    }
}
