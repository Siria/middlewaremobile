/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package temp.trasmettitori.JMS;

/**
 *
 * @author alessandra
 */
    public class MessageException extends Exception {

/********************************************************************
* Constructor.
********************************************************************/
public MessageException() {
  super();
}

/********************************************************************
* Constructor.
*
* @param info Information to be sent along with the exception.
********************************************************************/
public MessageException(String info) {
  super(info);
}
}
