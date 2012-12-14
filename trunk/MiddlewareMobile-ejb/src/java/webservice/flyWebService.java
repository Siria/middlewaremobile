/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

/**
 *
 * @author alessandra
 */
@WebService(serviceName = "flyWebService")
@Stateless()
public class flyWebService {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    public void openMethod(Object msg) {
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "operationMia")
    public String operationMia(@WebParam(name = "frase") String frase) {
        
        System.out.print(frase);
        System.out.println("Spero di non aver sbagliato");
        //TODO write your implementation code here:
        return null;
    }
    
    
    
    
    
    
}
