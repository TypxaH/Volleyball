import java.util.Set;

//Да се създаде клас волейболен отбор с полета: име, стадион, треньор и играчи.
public class VolleyballTeam implements Forceful{
    protected String name;
    protected String stadium;
    protected String coach;
    protected Set<VolleyballPlayer> players;

    public VolleyballTeam(String name, String stadium, String coach, Set<VolleyballPlayer> players) {
        this.name = name;
        this.stadium = stadium;
        this.coach = coach;
        this.players = players;
    }

    public int calculateStrength() {
        int sum = 0;
        for (VolleyballPlayer player : this.players) {
            sum += player.skills;
        }
        return sum / this.players.size();
    }

    public void teamTraining() {
        for (VolleyballPlayer player : this.players) {
            player.train();
        }
    }

    public void teamRest() {
        for (VolleyballPlayer player : this.players) {
            player.rest();
        }
    }

    @Override
    public String toString() {
        return "VolleyballTeam{" +
                "name='" + name + '\'' +
                ", stadium='" + stadium + '\'' +
                ", coach='" + coach + '\'' +
                ", players=" + players +
                '}';
    }
}
