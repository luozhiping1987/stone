package stone;

public abstract class 词类 {
    public static final 词类 EOF = new 词类(-1){}; // end of file
    public static final String EOL = "\\n";          // end of line 
    private int lineNumber;

    protected 词类(int line) {
        lineNumber = line;
    }
    public int getLineNumber() { return lineNumber; }
    public boolean 为标识符() { return false; }
    public boolean 为数() { return false; }
    public boolean isString() { return false; }
    public int getNumber() { throw new StoneException("not number token"); }
    public String 取文本() { return ""; }
}
