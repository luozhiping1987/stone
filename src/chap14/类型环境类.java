package chap14;
import java.util.Arrays;
import stone.StoneException;

public class 类型环境类 {
    protected 类型环境类 outer;
    protected 类型信息类[] types;
    public 类型环境类() { this(8, null); }
    public 类型环境类(int size, 类型环境类 out) {
        outer = out;
        types = new 类型信息类[size];
    }
    public 类型信息类 get(int nest, int index) {
        if (nest == 0)
            if (index < types.length)
                return types[index];
            else
                return null;
        else if (outer == null)
            return null;
        else
            return outer.get(nest - 1, index);
    }
    public 类型信息类 put(int nest, int index, 类型信息类 value) {
        类型信息类 oldValue;
        if (nest == 0) {
            access(index);
            oldValue = types[index];
            types[index] = value;
            return oldValue;    // may be null
        }
        else if (outer == null)
            throw new StoneException("no outer type environment");
        else
            return outer.put(nest - 1, index, value);
    }
    protected void access(int index) {
        if (index >= types.length) {
            int newLen = types.length * 2;
            if (index >= newLen)
                newLen = index + 1;
            types = Arrays.copyOf(types, newLen);
        }
    }
}
