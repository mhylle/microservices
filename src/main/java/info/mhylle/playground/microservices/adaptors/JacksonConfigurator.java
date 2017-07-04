package info.mhylle.playground.microservices.adaptors;

import javax.ws.rs.ext.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@Provider
public class JacksonConfigurator implements ContextResolver<ObjectMapper>
{
  
  private ObjectMapper mapper = new ObjectMapper();
  
  public JacksonConfigurator()
  {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
  }
  
  @Override
  public ObjectMapper getContext(Class<?> type)
  {
    return mapper;
  }
}