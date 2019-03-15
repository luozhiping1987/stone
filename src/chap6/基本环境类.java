package chap6;
import java.util.HashMap;

public class 基本环境类 implements Environment {
    protected HashMap<String,Object> values;
    public 基本环境类() { values = new HashMap<String,Object>(); }
    public void put(String name, Object value) { values.put(name, value); }
    public Object get(String name) { return values.get(name); }
}
