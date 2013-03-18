
package analisi;

/**
 *
 * @author alessandra
 */
public class Aereo {
    
    // l'id degli aerei saranno 01, 02, 03, 04, 05 e l'is rappresenta anche l'ordine
    // degli aerei
    //bisogna creare un'associazione tramite l'aereo e la sua struttura
    int id;
    String position;
    int followID;
    int prevID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Aereo(String position, int follower, int previous) {
        this.position = position;
        this.followID = follower;
        this.prevID = previous;
    }

    
    public String getPosition() {
        return position;
    }


    
    
}
