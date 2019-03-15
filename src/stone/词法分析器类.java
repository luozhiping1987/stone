package stone;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 词法分析器类 {
    public static String regexPat
        = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
          + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<词类> queue = new ArrayList<词类>();
    private boolean hasMore;
    private LineNumberReader reader;

    public 词法分析器类(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }
    public 词类 读() throws 分析例外 {
        if (fillQueue(0))
            return queue.remove(0);
        else
            return 词类.EOF;
    }
    public 词类 瞄(int i) throws 分析例外 {
        if (fillQueue(i))
            return queue.get(i);
        else
            return 词类.EOF; 
    }
    private boolean fillQueue(int i) throws 分析例外 {
        while (i >= queue.size())
            if (hasMore)
                readLine();
            else
                return false;
        return true;
    }
    protected void readLine() throws 分析例外 {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new 分析例外(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNo, matcher);
                pos = matcher.end();
            }
            else
                throw new 分析例外("bad token at line " + lineNo);
        }
        queue.add(new IdToken(lineNo, 词类.EOL));
    }
    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null) // if not a space
            if (matcher.group(2) == null) { // if not a comment
                词类 token;
                if (matcher.group(3) != null)
                    token = new NumToken(lineNo, Integer.parseInt(m));
                else if (matcher.group(4) != null)
                    token = new StrToken(lineNo, toStringLiteral(m));
                else
                    token = new IdToken(lineNo, m);
                queue.add(token);
            }
    }
    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\')
                    c = s.charAt(++i);
                else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    protected static class NumToken extends 词类 {
        private int value;

        protected NumToken(int line, int v) {
            super(line);
            value = v;
        }
        public boolean 为数() { return true; }
        public String 取文本() { return Integer.toString(value); }
        public int getNumber() { return value; }
    }

    protected static class IdToken extends 词类 {
        private String text; 
        protected IdToken(int line, String id) {
            super(line);
            text = id;
        }
        public boolean 为标识符() { return true; }
        public String 取文本() { return text; }
    }

    protected static class StrToken extends 词类 {
        private String literal;
        StrToken(int line, String str) {
            super(line);
            literal = str;
        }
        public boolean isString() { return true; }
        public String 取文本() { return literal; }
    }
}
