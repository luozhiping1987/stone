package chap3;
import stone.*;
import java.io.FileNotFoundException;

public class FileLexerRunner {
    public static void main(String[] args) throws 分析例外 {
        try {
            词法分析器类 l = new 词法分析器类(CodeDialog.file());
            for (词类 t; (t = l.读()) != 词类.EOF; )
                System.out.println("=> " + t.取文本());
        }
        catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
