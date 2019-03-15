package chap3;
import stone.*;
import java.io.FileNotFoundException;

public class FileLexerRunner {
    public static void main(String[] args) throws ParseException {
        try {
            词法分析器类 l = new 词法分析器类(CodeDialog.file());
            for (词类 t; (t = l.读()) != 词类.EOF; )
                System.out.println("=> " + t.getText());
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
