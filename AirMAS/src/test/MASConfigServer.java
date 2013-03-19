/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import blocco.adapter.AdapterAlgoritmo;
import blocco.adapter.AdapterRicevitore;
import blocco.adapter.AdapterTrasmettitore;
import blocco.proxy.AlgoritmoProxy;
import blocco.proxy.ProxyTarget;
import blocco.proxy.RicevitoreProxy;
import blocco.proxy.TrasmettitoreProxy;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Seby
 */
public class MASConfigServer extends javax.swing.JFrame {
    
    static LinkedList<Thread> Threads = new LinkedList<Thread>();
    Applicazione applicazione = new Applicazione();
    
    /**
     * Creates new form MASConfigClient
     */
    public MASConfigServer() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jTextConfig = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaXML = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AirMAS - Server Configurazione");
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gfx/AirMASsc.png"))); // NOI18N

        jButton9.setText("Ascolta");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jTextConfig.setText("60000");
        jTextConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextConfigActionPerformed(evt);
            }
        });

        jLabel2.setText("Porta Configuratore");

        jTextAreaXML.setColumns(20);
        jTextAreaXML.setRows(5);
        jScrollPane1.setViewportView(jTextAreaXML);

        jMenu1.setText("File");

        jMenuItem4.setText("Esci");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1)
                    .add(layout.createSequentialGroup()
                        .add(jLabel1)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jTextConfig, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton9)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 236, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton9)
                    .add(jTextConfig, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        try{
            new JTextAreaStream().redirectSystemOutputTo(jTextAreaXML);
        
            final blocco.Blocco asc = new blocco.Blocco();
            asc.getConf().put("socketIngresso",jTextConfig.getText());
            AdapterRicevitore r = (new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName("ricezione.RicevitoreSocket").newInstance())));
            r.config(asc.getMonitor(), asc.getConf());
            
            Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    jTextAreaXML.setText("In ascolto sulla porta "+jTextConfig.getText()+"...");
                                    while (true){
                                        Object tmp = asc.getMonitor().prelevaMessaggio(); //questi lasciamoli object
                                        jTextAreaXML.append("\nHo ricevuto un messaggio di configurazione...");
                                        
                                        createDocument(tmp.toString());
                                        
                                    }
                                 
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            }                            
                        });
                        t.start();	
            
           
        }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jTextConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextConfigActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private class Applicazione{
        private LinkedList<Blocco> blocchi = new LinkedList<>();
    
        @Override
        public String toString(){
             StringBuilder testo = new StringBuilder("<AirMAS>");
                for (Blocco b : blocchi){
                    testo.append(b.toString());
                }
                testo.append("\n</AirMAS>");
            return testo.toString();
        }
        
        public Blocco get(String s){
            
            for (Blocco b : blocchi){
                if (b.id.equals(s)){
                    return b;
                }
            }
            return null;
        }
        
        public boolean remove(String s){
            for (Blocco b : blocchi){
                if (b.id.equals(s)){
                    return blocchi.remove(b);
                }
            }
            return false;
        }
        
    }
    
    private class Blocco{
        public String id;
        public LinkedList<Elemento> trasmettitori = new LinkedList<>();
        public LinkedList<Elemento> ricevitori = new LinkedList<>();
        public Elemento algoritmo = new Elemento();

        public Blocco(String id) {
            this.id = id;
        }
        
        @Override
        public boolean equals(Object e){
            return ((Blocco)e).id.equals(this.id);
        }
        
        @Override
        public String toString(){
            StringBuilder testo = new StringBuilder("\n\t<blocco id=\""+id+"\">\n\t\t<trasmettitori>");
                    
            for (Elemento e : trasmettitori){
                testo.append("\n\t\t\t<trasmettitore>").append(e.toString()).append("\n\t\t\t</trasmettitore>");
                
            }        
            testo.append("\n\t\t</trasmettitori>\n\t\t<ricevitori>");
            
            
            for (Elemento e : ricevitori){
                testo.append("\n\t\t\t<ricevitore>").append(e.toString()).append("\n\t\t\t</ricevitore>");
            }        
            
            testo.append("\n\t\t</ricevitori>\n\t\t<algoritmo>").append(algoritmo.toString()).append("\n\t\t</algoritmo>\n\t</blocco>");
            
            return testo.toString();
        }
    }
    
    private class Elemento{
        public LinkedList<Valore> valori = new LinkedList<>();
        
        public String toString (){
            StringBuilder testo = new StringBuilder();
            for (Valore e : valori){
                testo.append("\n\t\t\t\t").append(e.toString());
            }
            return testo.toString();
        }
    }
    
    private class Valore{
        public String nome;
        public String prop;
        public String valore;

        public Valore(String nome, String prop, String valore) {
            this.nome = nome;
            this.prop = prop;
            this.valore = valore;
        }
        
        @Override
        public String toString (){
            if (prop.equals("")){
                return "<"+nome+">"+valore+"</"+nome+">";
            } else {
                return "<"+nome+" key=\""+prop+"\">"+valore+"</"+nome+">";
            }
        }
    }
    
  private void createDocument(String s){  
        jTextAreaXML.append("\n\n\n\nAttendere prego...\n");
        jTextAreaXML.setCaretPosition(jTextAreaXML.getText().length());
            
        for (Thread th : Threads){
            th.interrupt();
        }
        
        try {
                  DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                  
                  InputSource source = new InputSource(new StringReader(s));
                  Document doc = dBuilder.parse(source);
                  
                  if (doc.hasChildNodes()) {
                          parse(doc.getChildNodes().item(0).getChildNodes());  
                  }
                  
                  Thread.sleep(6000);
            
            jTextAreaXML.append("\n\n\n\n ----------------------------------------");
            jTextAreaXML.append("\n ----- Middleware for Autonomic Systems -----");
            jTextAreaXML.append("\n ----------------------------------------");
            jTextAreaXML.append("\n\nTutto OK: avviare un Client...\n\n\n");
            jTextAreaXML.setCaretPosition(jTextAreaXML.getText().length());
                  
                  
                  
        } catch (InterruptedException ex) {
            Logger.getLogger(MASConfigServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(MASConfigServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
  }
  
  
  private static void parse(NodeList nodeList) {
 try{
    for (int count = 0; count < nodeList.getLength(); count++) {
	Node tempNode = nodeList.item(count);
	if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
                if (tempNode.getNodeName().equals("blocco")){
                    
                    blocco.Blocco blocco = new blocco.Blocco();
                    
                    if (tempNode.hasChildNodes()){   
                        NodeList listaTrasmettitori = tempNode.getChildNodes().item(1).getChildNodes(); // Trasmettitori
                            for (int c = 0; c < listaTrasmettitori.getLength(); c++){
                                Node trasmettitore = listaTrasmettitori.item(c);
                                if (trasmettitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                //System.out.println(trasmettitore.getNodeName());
                                NodeList valoriTrasmettitore = trasmettitore.getChildNodes();
                                    for (int d = 0; d < valoriTrasmettitore.getLength(); d++){
                                        Node valoreTrasmettitore = valoriTrasmettitore.item(d);
                                        if (valoreTrasmettitore.getNodeType() == Node.ELEMENT_NODE) {
         
                                        //System.out.println(valoreTrasmettitore.getNodeName());
                                
                                        switch (valoreTrasmettitore.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreTrasmettitore.getNodeValue()+"+"+valoreTrasmettitore.getTextContent());
                                                blocco.getTrasmettitori().add(new AdapterTrasmettitore((TrasmettitoreProxy)ProxyTarget.createProxy(Class.forName(valoreTrasmettitore.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreTrasmettitore.getNodeValue()+"+"+valoreTrasmettitore.getTextContent()+"+"+valoreTrasmettitore.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreTrasmettitore.getAttributes().item(0).getNodeValue(), valoreTrasmettitore.getTextContent());
                                            break;     
                                        }
                                    }
                            }
                        }
                        }
                        NodeList listaRicevitori = tempNode.getChildNodes().item(3).getChildNodes(); // Ricevitori 
                            for (int c = 0; c < listaRicevitori.getLength(); c++){
                                
                                Node ricevitore = listaRicevitori.item(c);
                                if (ricevitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                //System.out.println(ricevitore.getNodeName());
                                
                                NodeList valoriRicevitore = ricevitore.getChildNodes();
                                    for (int d = 0; d < valoriRicevitore.getLength(); d++){
                                        Node valoreRicevitore = valoriRicevitore.item(d);
                                        if (valoreRicevitore.getNodeType() == Node.ELEMENT_NODE) {
        
                                        //System.out.println(valoreRicevitore.getNodeName());
                                
                                        switch (valoreRicevitore.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreRicevitore.getNodeValue()+"+"+valoreRicevitore.getTextContent());
                                                blocco.getRicevitori().add(new AdapterRicevitore((RicevitoreProxy)ProxyTarget.createProxy(Class.forName(valoreRicevitore.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreRicevitore.getNodeValue()+"+"+valoreRicevitore.getTextContent()+"+"+valoreRicevitore.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreRicevitore.getAttributes().item(0).getNodeValue(), valoreRicevitore.getTextContent());
                                            break;     
                                        }
                                    }
                            }
                                }}
                        NodeList valoriAlgoritmo = tempNode.getChildNodes().item(5).getChildNodes(); // Algoritmo
                                    for (int d = 0; d < valoriAlgoritmo.getLength(); d++){
                                        Node valoreAlgoritmo = valoriAlgoritmo.item(d);
                                        if (valoreAlgoritmo.getNodeType() == Node.ELEMENT_NODE) {
        
                                        switch (valoreAlgoritmo.getNodeName()){
                                            case("class"):
                                                //System.out.println(valoreAlgoritmo.getNodeValue()+"+"+valoreAlgoritmo.getTextContent());
                                                blocco.setAlgoritmo(new AdapterAlgoritmo((AlgoritmoProxy)ProxyTarget.createProxy(Class.forName(valoreAlgoritmo.getTextContent()).newInstance())));        
                                                break;
                                                
                                            case("config"):
                                                //System.out.println(valoreAlgoritmo.getNodeValue()+"+"+valoreAlgoritmo.getTextContent()+"+"+valoreAlgoritmo.getAttributes().item(0).getNodeValue());
                                                blocco.getConf().put(valoreAlgoritmo.getAttributes().item(0).getNodeValue(), valoreAlgoritmo.getTextContent());
                                            break;     
                                        }
                                    }
                                    } 
                    }
                    Thread th = new Thread (blocco);
                    Threads.add(th);
                    th.start();

                    Thread.sleep(1000);
                }
                
                
                //--------------------------------------------------------------------
                       
        }
 
  }
    } catch(Exception e){
        e.printStackTrace();
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MASAirplaneClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MASConfigServer().setVisible(true);
            }
        });
    }
    
    
    


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaXML;
    private javax.swing.JTextField jTextConfig;
    // End of variables declaration//GEN-END:variables
}
