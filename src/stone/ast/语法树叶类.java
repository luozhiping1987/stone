package stone.ast;
import java.util.Iterator;
import java.util.ArrayList;
import stone.词类;

public class 语法树叶类 extends 语法树类 {
    private static ArrayList<语法树类> empty = new ArrayList<语法树类>(); 
    protected 词类 token;
    public 语法树叶类(词类 t) { token = t; }
    public 语法树类 子(int i) { throw new IndexOutOfBoundsException(); }
    public int 子个数() { return 0; }
    public Iterator<语法树类> children() { return empty.iterator(); }
    public String toString() { return token.取文本(); }
    public String location() { return "at line " + token.getLineNumber(); }
    public 词类 词() { return token; }
}
