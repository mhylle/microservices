package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.Period;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/periods")
public class Periods {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Period> getAddresses() {
    return Repository.getInstance().getPeriods();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Period getPeriod(@PathParam("id") String id) {
    return Repository.getInstance().getPeriods().stream()
        .filter(period -> period.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }

  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getPeriods().size();
  }

  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getPeriods().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addPeriod(Period period) {
    Repository.getInstance().addPeriod(period);
  }
}
