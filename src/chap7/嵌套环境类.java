package chap7;
import java.util.HashMap;
import chap6.Environment;
import chap7.函数求值器类.EnvEx;

public class 嵌套环境类 implements Environment {
    protected HashMap<String,Object> values;
    protected Environment outer;
    public 嵌套环境类() { this(null); }
    public 嵌套环境类(Environment e) {
        values = new HashMap<String,Object>();
        outer = e;
    }
    public void setOuter(Environment e) { outer = e; }
    public Object get(String name) {
        Object v = values.get(name);
        if (v == null && outer != null)
            return outer.get(name);
        else
            return v;
    }
    public void putNew(String name, Object value) { values.put(name, value); }
    public void put(String name, Object value) {
        Environment e = where(name);
        if (e == null)
            e = this;
        ((EnvEx)e).putNew(name, value);
    }
    public Environment where(String name) {
        if (values.get(name) != null)
            return this;
        else if (outer == null)
            return null;
        else
            return ((EnvEx)outer).where(name);
    }
}
