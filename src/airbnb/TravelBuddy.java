package airbnb;

import java.util.*;
import java.util.stream.Collectors;

public class TravelBuddy {

  /**
   * findTravelBuddies
   * @param toGoCities
   * @param person
   * @param friends
   * @return
   */
  public List<String> findTravelBuddies(Map<String, List<String>> toGoCities, String person, List<String> friends) {
    Set<String> cities = new HashSet<>(toGoCities.get(person));
    List<Buddy> buddies = new ArrayList<>();

    for(String friend : friends) {
      double simi = getSimilarity(friend, toGoCities, cities);
      if(simi >= 0.5) {
        buddies.add(new Buddy(friend, simi));
      }
    }
    buddies.sort((a,b) -> Double.compare(b.similarity, a.similarity));

    // Recommand Cities
    Set<String> recommandedCities = new LinkedHashSet<>();
    for(Buddy buddy : buddies) {
      for(String city : toGoCities.get(buddy.name)) {
        if(!cities.contains(city)) {
          recommandedCities.add(city);
        }
      }
    }
    //return buddies.stream().map(i -> i.name).collect(Collectors.toList());
    return new ArrayList<>(recommandedCities);
  }

  private double getSimilarity(String friend, Map<String, List<String>> toGoCities, Set<String> cities) {
    List<String> friendCities = toGoCities.get(friend);
    int common = 0;
    for(String city : friendCities) {
      if(cities.contains(city)) {
        common++;
      }
    }
    return 1.0 * common / cities.size();
  }

  private class Buddy {
    String name;
    double similarity;

    public Buddy(String name, double similarity) {
      this.name = name;
      this.similarity = similarity;
    }
  }
}
