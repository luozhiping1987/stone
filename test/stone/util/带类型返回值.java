package stone.util;

import chap14.TypeInfo;

public class 带类型返回值 {
  public Object 返回值;
  public TypeInfo 类型信息;
  public 带类型返回值(Object 返回值, TypeInfo 类型信息) {
    this.返回值 = 返回值;
    this.类型信息 = 类型信息;
  }
}
