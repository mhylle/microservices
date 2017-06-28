package info.mhylle.playground.microservices.routes;

import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.Address;

@Path("/addresses")
public class Addresses {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Address> getAddresses() {
    return Repository.getInstance().getAddresses();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Address getAddress(@PathParam("id") String id) {
    return Repository.getInstance().getAddresses().stream()
        .filter(address -> address.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }
  
  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getAddresses().size();
  }
  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getAddresses().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addAddress(Address address) {
    Repository.getInstance().addAddress(address);
  }

}
