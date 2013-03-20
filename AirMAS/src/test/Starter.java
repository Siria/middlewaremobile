/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.HashMap;
import javax.swing.UIManager;

/**
 *
 * @author Seby
 */
public class Starter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MASAirplaneClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                HashMap<String,String> conf = new HashMap();
                conf.put("MASclass", "trasmissione.TrasmettitoreSocket");
                conf.put("MASvalue", "localhost:50000");
                conf.put("MASconfig", "socketUscita");
                
                conf.put("AEREOclass", "ricezione.RicevitoreSocket");
                conf.put("AEREOconfig", "socketIngresso");
                
                conf.put("AEREOvalue", "40005");
                new MASAirplaneClient(conf).setVisible(true);
                
                conf.put("AEREOvalue", "40004");
                new MASAirplaneClient(conf).setVisible(true);
                
                conf.put("AEREOvalue", "40003");
                new MASAirplaneClient(conf).setVisible(true);
                
                conf.put("AEREOvalue", "40002");
                new MASAirplaneClient(conf).setVisible(true);
                
                conf.put("AEREOvalue", "40001");
                new MASAirplaneClient(conf).setVisible(true);
                
                
                
                
                
                
                
                
                new MASConfigClient().setVisible(true);
                
                new MASConfigServer().setVisible(true);
                
            }
        });
       
    }
}
