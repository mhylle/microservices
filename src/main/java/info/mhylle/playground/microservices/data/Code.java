package info.mhylle.playground.microservices.data;

public class Code
{
  private String id;
  private String code;
  private String text;
  
  Code()  {
  }
  
  public String getId()
  {
    return id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getCode()
  {
    return code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getText()
  {
    return text;
  }
  
  public void setText(String text)
  {
    this.text = text;
  }
}
