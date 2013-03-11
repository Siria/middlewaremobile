/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.Pipe;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author Seby
 */
class JTextAreaStream {

private JTextArea area;

public void redirectSystemOutputTo(JTextArea textArea) throws IOException {
area = textArea;
Pipe pipe = Pipe.open();
OutputStream out = Channels.newOutputStream(pipe.sink());
final InputStream in = Channels.newInputStream(pipe.source());
Runnable task = new Runnable() {

public void run() {
loopRead(in);
}
};
System.setOut(new PrintStream(out));
Thread reader = new Thread(task, "Stream reader");
reader.setDaemon(true);
reader.start();
}

private void loopRead(InputStream stream) {
Scanner in = new Scanner(new InputStreamReader(stream));
while (in.hasNextLine()) {
appendLine(in.nextLine());
}
}

private void appendLine(final String text) {
EventQueue.invokeLater(new Runnable() {

public void run() {
area.append(text);
area.append("\n");
}
});
}
}
