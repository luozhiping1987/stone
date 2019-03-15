package chap13;
import chap11.可变长度数组环境类;

public class 虚拟机环境类 extends 可变长度数组环境类 implements HeapMemory {
    protected StoneVM svm;
    protected Code code;
    public 虚拟机环境类(int codeSize, int stackSize, int stringsSize) {
        svm = new StoneVM(codeSize, stackSize, stringsSize, this);
        code = new Code(svm);
    }
    public StoneVM stoneVM() { return svm; }
    public Code code() { return code; }
    public Object read(int index) { return values[index]; }
    public void write(int index, Object v) { values[index] = v; }
}
