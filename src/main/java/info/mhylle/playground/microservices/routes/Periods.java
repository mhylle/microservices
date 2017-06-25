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
  public List<Period> getPeriods() {
    return Repository.getInstance().getPeriods();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addAddress(Period period) {
    Repository.getInstance().addPeriod(period);
  }
}
