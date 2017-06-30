//package info.mhylle.playground.microservices.adaptors;
//
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.ext.Provider;
//
//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
//import org.codehaus.jackson.map.*;
//
//@Provider
//public class MessageBodyWriteJSON extends JacksonJsonProvider
//{
//  public MessageBodyWriteJSON()
//  {
//  }
//
//  @Override
//  public ObjectMapper locateMapper(
//    Class<?> type,
//    MediaType mediaType)
//  {
//    ObjectMapper mapper = super.locateMapper(type, mediaType);
//    mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
//    return mapper;
//  }
//}
