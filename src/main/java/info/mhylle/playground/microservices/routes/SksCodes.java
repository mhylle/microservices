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

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public SKSCode getSksCode(@PathParam("id") String id) {
    return Repository.getInstance().getSksCodes().stream()
        .filter(sksCode -> sksCode.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }

  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getSksCodes().size();
  }
  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getSksCodes().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addSksCode(SKSCode sksCode) {
    Repository.getInstance().addSksCode(sksCode);
  }
}
