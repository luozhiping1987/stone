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
            + "|[\\p{script=Han}A-Z_a-z][\\p{script=Han}A-Z_a-z0-9]*" // 支持中文标识符
            + "|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern 模式 = Pattern.compile(regexPat);
    private ArrayList<词类> queue = new ArrayList<>();
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
        String 行;
        try {
            行 = reader.readLine();
        } catch (IOException e) {
            throw new 分析例外(e);
        }
        if (行 == null) {
            hasMore = false;
            return;
        }
        int 行数 = reader.getLineNumber();
        Matcher 匹配器 = 模式.matcher(行);
        匹配器.useTransparentBounds(true).useAnchoringBounds(false);
        int 头 = 0;
        int 尾 = 行.length();
        while (头 < 尾) {
            匹配器.region(头, 尾);
            if (匹配器.lookingAt()) {
                添加词(行数, 匹配器);
                头 = 匹配器.end();
            }
            else
                throw new 分析例外("bad token at line " + 行数);
        }
        queue.add(new IdToken(行数, 词类.EOL));
    }
    protected void 添加词(int lineNo, Matcher matcher) {
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
        @Override
        public boolean 为数() { return true; }
        @Override
        public String 取文本() { return Integer.toString(value); }
        @Override
        public int getNumber() { return value; }
    }

    protected static class IdToken extends 词类 {
        private String text;
        protected IdToken(int line, String id) {
            super(line);
            text = id;
        }
        @Override
        public boolean 为标识符() { return true; }
        @Override
        public String 取文本() { return text; }
    }

    protected static class StrToken extends 词类 {
        private String literal;
        StrToken(int line, String str) {
            super(line);
            literal = str;
        }
        @Override
        public boolean isString() { return true; }
        @Override
        public String 取文本() { return literal; }
    }
}
