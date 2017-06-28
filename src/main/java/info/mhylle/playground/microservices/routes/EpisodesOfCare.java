package info.mhylle.playground.microservices.routes;

import info.mhylle.playground.microservices.data.Repository;
import info.mhylle.playground.microservices.model.EpisodeOfCare;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/episodesofcare")
public class EpisodesOfCare {
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<EpisodeOfCare> getAddresses() {
    return Repository.getInstance().getEpisodesOfCare();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public EpisodeOfCare getAddress(@PathParam("id") String id) {
    return Repository.getInstance().getEpisodesOfCare().stream()
        .filter(episodeOfCare -> episodeOfCare.getIdentifier().equals(id))
        .findFirst().orElse(null);
  }

  @GET
  @Path("amount")
  public int getAmount() {
    return Repository.getInstance().getEpisodesOfCare().size();
  }

  @GET
  @Path("nextIdentifier")
  @Produces(MediaType.APPLICATION_JSON)
  public int getNextIdentifier() {
    return Repository.getInstance().getEpisodesOfCare().size();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addEpisodeOfCare(EpisodeOfCare episodeOfCare) {
    Repository.getInstance().addEpisodeOfCare(episodeOfCare);
  }
}
