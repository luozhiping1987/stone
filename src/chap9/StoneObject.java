package chap9;
import chap6.环境类;
import chap7.函数求值器类.EnvEx;

public class StoneObject {
    public static class AccessException extends Exception {}
    protected 环境类 env;
    public StoneObject(环境类 e) { env = e; }
    @Override public String toString() { return "<object:" + hashCode() + ">"; }
    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);
    }
    public void write(String member, Object value) throws AccessException {
        ((EnvEx)getEnv(member)).putNew(member, value);
    }
    protected 环境类 getEnv(String member) throws AccessException {
        环境类 e = ((EnvEx)env).where(member);
        if (e != null && e == env)
            return e;
        else
            throw new AccessException();
    }
}
