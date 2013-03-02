
package analisi;

/**
 *
 * @author alessandra
 */
public class Aereo {
    String position;
    String follower;
    String previous;

    public Aereo() {
    }

    public Aereo(String position, String follower, String previous) {
        this.position = position;
        this.follower = follower;
        this.previous = previous;
    }

    
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }
    
    
}
