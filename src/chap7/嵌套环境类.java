package chap7;
import java.util.HashMap;
import chap6.环境类;
import chap7.函数求值器类.EnvEx;

public class 嵌套环境类 implements 环境类 {
    protected HashMap<String,Object> values;
    protected 环境类 outer;
    public 嵌套环境类() { this(null); }
    public 嵌套环境类(环境类 e) {
        values = new HashMap<String,Object>();
        outer = e;
    }
    public void setOuter(环境类 e) { outer = e; }
    public Object get(String name) {
        Object v = values.get(name);
        if (v == null && outer != null)
            return outer.get(name);
        else
            return v;
    }
    public void putNew(String name, Object value) { values.put(name, value); }
    public void put(String name, Object value) {
        环境类 e = where(name);
        if (e == null)
            e = this;
        ((EnvEx)e).putNew(name, value);
    }
    public 环境类 where(String name) {
        if (values.get(name) != null)
            return this;
        else if (outer == null)
            return null;
        else
            return ((EnvEx)outer).where(name);
    }
}
