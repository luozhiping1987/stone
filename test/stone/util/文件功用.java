package stone.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class 文件功用 {
  public static String 读文件(String 路径, Charset 编码)
      throws IOException
    {
      byte[] 内容 = Files.readAllBytes(Paths.get(路径));
      return new String(内容, 编码);
    }
}
