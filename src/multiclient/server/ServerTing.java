/**
 *Navn: Elias Andreassen Thøgersen
 *Studentnr: s236603
 *Klasse: INFORMATIK14HA
 * 
 *Navn: Synne Storhaug
 *Studentnr: s236636
 *Klasse: INFORMATIK14HA 
 * 
 *Navn: Christopher Toussi
 *Studentnr: s163502
 *Klasse: INFORMATIK14HA
 * 
 *Navn: Johannes Strige
 *Studentnr: s236652
 *Klasse: INFORMATIK14HA 
 **/
package multiclient.server;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

//waits for a client to connect in a separate thread using Java's Task class
public class ServerTing extends Task<Void> {
    
    boolean forste = true;
    private final int portNumber;
    private String fargeTing, client;

    public ServerTing(int portNumber)
    {
        this.portNumber = portNumber;
    }

    @Override
    public Void call() throws Exception
    {
        try (ServerSocket serverSocket = new ServerSocket(portNumber))
        {
            while (true)
            {
                
                Socket conn = serverSocket.accept();
                ClientService cs = new ClientService(conn);
                client = conn.getInetAddress().getHostAddress();
                if(forste) { 
                    ServerVindu.klienter.appendText(client + "\n");
                    forste = false;
                }
                cs.start();
            }
        } catch (IOException e)
        {
            System.out.println("Exception!!! "+e.getMessage());
        }

        return null;
    }

    public void start()
    {
        Thread t = new Thread(this);
        t.start();
    }

    //class which serves a client in a separate thread using Java's Service class
    private class ClientService extends Service<Void> {
        
        Socket connectSocket;
        private final String client;

        public ClientService(Socket connectSocket)
        {
            
            this.connectSocket = connectSocket;
            client = connectSocket.getInetAddress().getHostAddress();
        }

        @Override
        protected Task<Void> createTask() {
            
            Task<Void> task =  new Task<Void>() {
                
                @Override
                public Void call() throws InterruptedException {

                    try (PrintWriter out = new PrintWriter(connectSocket.getOutputStream(), true);) {
                        
                        if (ServerVindu.group.getSelectedToggle() == ServerVindu.red) {
                            fargeTing = "Rød";
                        } else if (ServerVindu.group.getSelectedToggle() == ServerVindu.yellow) {
                            fargeTing = "Gul";
                        } else if (ServerVindu.group.getSelectedToggle() == ServerVindu.green) {
                            fargeTing = "Grønn";
                        } else {
                            fargeTing = "off";
                        }
                        out.println((int)Math.round(ServerVindu.slider.getValue()) 
                            + "," + (int)Math.round(ServerVindu.slider2.getValue()) 
                            + "," + (int)Math.round(ServerVindu.slider3.getValue()) 
                            + "," + fargeTing);
                    } catch (IOException e) {
                        System.out.println("Exception!!! "+e.getMessage());
                    }
                    return null;
                }
            };
            return task ;
        }
    }
}
