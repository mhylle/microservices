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
  public List<Encounter> getEncounters() {
    return Repository.getInstance().getEncounters();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addEncounter(Encounter encounter) {
    Repository.getInstance().addEncounter(encounter);
  }
}
