package info.mhylle.playground.microservices.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EpisodeOfCare
{
  UUID identifier;
  int responsibleUnit;
  int status;
}
