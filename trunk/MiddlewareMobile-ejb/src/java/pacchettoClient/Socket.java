/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pacchettoClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 *
 * @author alessandra
 */
public abstract class Socket {

    public abstract Socket createSingleSocket();
    private PrintWriter out;
    private BufferedReader in;
    
    public void openSocketConnection() throws Exception {
        Socket socket = createSingleSocket();
        socket.setSoTimeout(1000);
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8")), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
}
/**
 * Closes socket and output input streams.
 */
    public void closeSocketConnection() throws IOException {
        Socket.close();
        out.close();
        in.close();
}

/**
 * Writes the input to the socket and returns the output response
 * 
 * @param input
 *            a String to write on the socket
 * @return output a String that the socket returns
 * @throws Exception
 */
    public String writeReadSocket(String input) throws Exception {
        openSocketConnection();
        out.println(input);
        //logger.debug("Socket Input:" + input);
        String output = in.readLine();
        //logger.debug("Socket output:" + output);
        closeSocketConnection();
        return output;
        }

/**
 * Method overiden by Spring. We use the method injection technique, so that we have a new socket instance in every call to openSocketConnection()
 * method
 */

    private OutputStream getOutputStream() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void setSoTimeout(int i) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private InputStream getInputStream() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    private static void close() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
