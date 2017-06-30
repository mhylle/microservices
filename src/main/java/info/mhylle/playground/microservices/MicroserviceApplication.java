//package info.mhylle.playground.microservices;
//
//import java.util.*;
//
//import javax.ws.rs.core.Application;
//
//import info.mhylle.playground.microservices.adaptors.JacksonConfigurator;
//
///**
// * Created by mnh on 30-06-2017.
// */
//public class MicroserviceApplication extends Application
//{
//
//  @Override
//  public Set<Class<?>> getClasses()
//  {
//    System.out.println("MicroserviceApplication - getClasses: "  + "");
//    Set<Class<?>>  classes = new HashSet<>();
//    classes.add(JacksonConfigurator.class);
//    return classes;
//  }
//}
