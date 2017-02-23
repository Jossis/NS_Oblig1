package multiclient.server;

import javafx.collections.*;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//class waits for a client to connect in a separate thread using Java's Task<> class
public class ServerTask extends Task<Void>
{
    int portNumber;

    private ObservableMap<String, String> clientMessageMap = FXCollections.observableMap(new HashMap<>());
    public ObservableMap<String, String> getClientMessageMap() {return clientMessageMap;}

    public ServerTask(int portNumber)
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
                String client = conn.getInetAddress().getHostAddress();

                // Listens to the changes in the messageProperty of cs object
                cs.messageProperty().addListener((obs, oldMessage, newMessage) ->
                {
                    switch(newMessage.toLowerCase())
                    {
                        case "connected":
                            clientMessageMap.put(client, "");break;
                        case "disconnected":
                            clientMessageMap.remove(client);break;
                        default:
                            clientMessageMap.put(client,newMessage);
                    }
                    updateMessage(client + ": " + newMessage);
                });

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

    /**
     *  ClientService class which serves a client in a separate thread using (extending) Java's Service<> class
     */
    private static class ClientService extends Service<Void>
    {
        Socket connectSocket;
        private String client;

        public ClientService(Socket connectSocket)
        {
            this.connectSocket = connectSocket;
            client = connectSocket.getInetAddress().getHostAddress();
        }

        @Override
        protected Task<Void> createTask()
        {
            Task<Void> task =  new Task<Void>()
            {
                @Override
                public Void call() throws InterruptedException
                {
                    updateMessage("Connected");

                    try (PrintWriter out = new PrintWriter(connectSocket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(connectSocket.getInputStream())))
                    {
                        String inText;
                        while ((inText = in.readLine()) != null)
                        {
                            updateMessage("<< " + inText);

                            String outText = ProcessString(inText);
                            out.println(outText);

                            updateMessage(">> " + outText);
                        }
                    } catch (IOException e)
                    {
                        System.out.println("Exception!!! "+e.getMessage());
                    }
                    finally
                    {
                        updateMessage("Disconnected"); // null - indicate client disconnection
                    }
                    return null;
                }
            };
            return task ;
        }

        /**
         * Evaluate the intext if it is a valid binary expression. Otherwise, reverse the text.
         * @param intext
         * @return
         */
        private String ProcessString(String intext)
        {
            String outtext;

            Pattern p = Pattern.compile("(\\d*\\.?\\d+)\\s*([\\+\\-\\*/])\\s*(\\d*\\.?\\d+)");
            Matcher m = p.matcher(intext);
            if (m.matches())
            {
                double operand1 = Double.valueOf(m.group(1));
                char operator = m.group(2).charAt(0);
                double operand2 = Double.valueOf(m.group(3));

                double result=0;
                switch(operator)
                {
                    case '+': result = operand1 + operand2;break;
                    case '-': result = operand1 - operand2;break;
                    case '*': result = operand1 * operand2;break;
                    case '/': result = operand1 / operand2;break;
                }
                outtext = String.valueOf(result);
            }
            else
                outtext = new StringBuffer(intext).reverse().toString();

            return outtext;
        }
    }
}