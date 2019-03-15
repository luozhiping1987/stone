package chap11;
import java.util.Arrays;
import chap6.环境类;
import chap11.环境优化器类.环境执行类2;

public class 可变长度数组环境类 extends ArrayEnv {
    protected Symbols names;
    public 可变长度数组环境类() {
        super(10, null);
        names = new Symbols();
    }
    @Override public Symbols symbols() { return names; }
    @Override public Object get(String name) {
        Integer i = names.find(name);
        if (i == null)
            if (outer == null)
                return null;
            else
                return outer.get(name);
        else
            return values[i];
    }
    @Override public void put(String name, Object value) {
        环境类 e = where(name);
        if (e == null)
            e = this;
        ((环境执行类2)e).putNew(name, value);
    }
    @Override public void putNew(String name, Object value) {
        assign(names.putNew(name), value);
    }
    @Override public 环境类 where(String name) {
        if (names.find(name) != null)
            return this;
        else if (outer == null)
            return null;
        else
            return ((环境执行类2)outer).where(name);
    }
    @Override public void put(int nest, int index, Object value) {
        if (nest == 0)
            assign(index, value);
        else
            super.put(nest, index, value);
    }
    protected void assign(int index, Object value) {
        if (index >= values.length) {
            int newLen = values.length * 2;
            if (index >= newLen)
                newLen = index + 1;
            values = Arrays.copyOf(values, newLen);
        }
        values[index] = value;
    }
}
