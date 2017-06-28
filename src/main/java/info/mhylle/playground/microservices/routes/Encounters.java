package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.Encounter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/encounters")
public class Encounters {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Encounter> getAddresses() {
    return Repository.getInstance().getEncounters();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Encounter getEncounter(@PathParam("id") String id) {
    return Repository.getInstance().getEncounters().stream()
        .filter(encounter -> encounter.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }

  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getEncounters().size();
  }

  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getEncounters().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addEncounter(Encounter encounter) {
    Repository.getInstance().addEncounter(encounter);
  }
}
