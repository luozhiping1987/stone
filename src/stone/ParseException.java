package stone;
import java.io.IOException;

public class ParseException extends Exception {
    public ParseException(词类 t) {
        this("", t);
    }
    public ParseException(String msg, 词类 t) {
        super("syntax error around " + location(t) + ". " + msg);
    }
    private static String location(词类 t) {
        if (t == 词类.EOF)
            return "the last line";
        else
            return "\"" + t.getText() + "\" at line " + t.getLineNumber();
    }
    public ParseException(IOException e) {
        super(e);
    }
    public ParseException(String msg) {
        super(msg);
    }
}
