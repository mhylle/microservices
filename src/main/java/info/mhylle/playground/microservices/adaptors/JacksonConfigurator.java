package info.mhylle.playground.microservices.adaptors;

import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;

import org.codehaus.jackson.map.*;

/**
 * Created by mnh on 30-06-2017.
 */
public class JacksonConfigurator implements ContextResolver<ObjectMapper>
{

  private ObjectMapper mapper = new ObjectMapper();

  public JacksonConfigurator()
  {
    ObjectMapper mapper = new ObjectMapper();
    
    
      SerializationConfig config = mapper.getSerializationConfig();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM-yyyy HH:mm:ss");
    config.withDateFormat(df);
    DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
    deserializationConfig.withDateFormat(df);
    mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
  }

  @Override
  public ObjectMapper getContext(Class<?> type)
  {
    return mapper;
  }
}
