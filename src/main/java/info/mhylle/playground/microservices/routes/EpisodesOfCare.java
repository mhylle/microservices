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
  public List<EpisodeOfCare> getEpisodesOfCares() {
    return Repository.getInstance().getEpisodesOfCare();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public void addEpisodeOfCare(EpisodeOfCare episodeOfCare) {
    Repository.getInstance().addEpisodeOfCare(episodeOfCare);
  }
}
