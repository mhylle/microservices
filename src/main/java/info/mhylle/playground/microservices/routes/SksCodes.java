package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.SKSCode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/skscodes")
public class SksCodes {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<SKSCode> getSksCodes() {
    return Repository.getInstance().getSksCodes();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addSksCode(SKSCode sksCode) {
    Repository.getInstance().addSksCode(sksCode);
  }
}
