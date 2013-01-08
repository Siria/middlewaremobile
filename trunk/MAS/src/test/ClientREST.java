/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
*GET articles/3 recupera una rappresentazione dell'articolo desiderato
*POST articles/crea un nuovo articolo, la cui rappresentazione e nel body della richiesta
*PUT articles/54 modifica l'articolo esistente
*DELETE articles/ rimuove l'intera collezione di articoli
* 
* */

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;



public class ClientREST {

    public static void main(String args[]) 
    {
        invia("Client Rest..");
    }

    private static void invia(Object messaggio) {
        
        @Path("/test/ClientREST")
        class MessageRestService {
 
	@GET
	@Path("/{messaggio}")
	public Response printMessage(@PathParam("messaggio") Object messaggio) {
		String result = "Risultato restful: " + messaggio;
		return Response.status(200).entity(result).build();
        }
        
        @GET
        @Path("temp.ricevitori.REST/{messaggio}")
        public String getString(@PathParam("messaggio") Object messaggio ) {
        return messaggio.toString();
        }
        
        @POST
        @Path("add")
        @Produces("text/plain")
        @Consumes("application/xml")
        public String addString(String stringa) {
            System.out.println(stringa + " aggiunta in post");          
        throw new UnsupportedOperationException("Not yet implemented.");
        }
        
        }

    }
}





