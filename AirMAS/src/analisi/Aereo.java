
package analisi;

/**
 *
 * @author alessandra
 */
public class Aereo {
    
    // l'id degli aerei saranno 01, 02, 03, 04, 05 e l'is rappresenta anche l'ordine
    // degli aerei
    //bisogna creare un'associazione tramite l'aereo e la sua struttura
    String id;
    String position;
    int followID;
    int prevID;
    String previous;
    String follower;

    public String getPrevious() {
        return previous;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFollowID() {
        return followID;
    }

    public void setFollowID(int followID) {
        this.followID = followID;
    }

    public int getPrevID() {
        return prevID;
    }

    public void setPrevID(int prevID) {
        this.prevID = prevID;
    }





    public Aereo() {
    }

    public Aereo(String id, String position, int followID, int prevID) {
        this.id = id;
        this.position = position;
        this.followID = followID;
        this.prevID = prevID;
    }

    public Aereo(String id, String position) {
        this.id = id;
        this.position = position;
    }

   

    
    public String getPosition() {
        return position;
    }


    
    
}
