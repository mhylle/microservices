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

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public SORCode getSorCode(@PathParam("id") String id) {
    return Repository.getInstance().getSorCodes().stream()
        .filter(sorCode -> sorCode.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }

  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getSorCodes().size();
  }

  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getSorCodes().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addSorCode(SORCode sorCode) {
    Repository.getInstance().addSorCode(sorCode);
  }
}
