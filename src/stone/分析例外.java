package stone;
import java.io.IOException;

public class 分析例外 extends Exception {
    public 分析例外(词类 t) {
        this("", t);
    }
    public 分析例外(String msg, 词类 t) {
        super("syntax error around " + location(t) + ". " + msg);
    }
    private static String location(词类 t) {
        if (t == 词类.EOF)
            return "the last line";
        else
            return "\"" + t.getText() + "\" at line " + t.getLineNumber();
    }
    public 分析例外(IOException e) {
        super(e);
    }
    public 分析例外(String msg) {
        super(msg);
    }
}
