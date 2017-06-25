package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.SORCode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/sorcodes")
public class SorCodes {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<SORCode> getSorCodes() {
    return Repository.getInstance().getSorCodes();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addSorCode(SORCode sorCode) {
    Repository.getInstance().addSorCode(sorCode);
  }
}
