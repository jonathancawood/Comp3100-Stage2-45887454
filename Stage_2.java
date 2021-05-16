import java.io.*;
import java.net.*;
import java.util.*;



public class Stage_2 {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 50000;
    private static final String REDY = "REDY\n";
    private static final String HELO = "HELO\n";
    private static final String OK = "OK\n";
    private static final String NONE = "NONE\n";
    private static final String AUTH = "AUTH BLANK\n";
    private static final String DSSYSTEM_FILE_ADDRESs = "ds-system.xml";
    private static final String WHITE_SPACE = " ";
    private static final String GETS_CAPABLE = "GETS Capable";
    private static Socket socket;
    private static DataInputStream din;
    private static DataOutputStream dout;

    private static void handshake(DataInputStream din, DataOutputStream dout) {
        try {
            // first step
            dout.write(HELO.getBytes());
            String reply = din.readLine();
            System.out.println("Server says: " + reply);

            // second step
            dout.write(AUTH.getBytes());
            reply = din.readLine();
            System.out.println("Server says: " + reply);
        } catch (IOException e) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try { 
            // initialise socket
            socket = new Socket(ADDRESS,PORT);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            // Handshake
            handshake(din, dout);

            //ds-system.xml is available 
            List<Server> dsServers = parseDSSystemXML(ADDRESS);
            // to find out the server at the ds-sim server sde,
            dout.write(REDY.getBytes());
            String reply = din.readLine();
            System.out.println("Server says: "+reply);
            while(!reply.equals(NONE)){
                //parse the incoming message from ds-server
                List<String> parsedInfo = parseJOBNMessage(reply);                  //needs work 
                //parseInfo 0> jobId
                //1>> core
                //2>> mem
                //3>> disk 

                String[] value = hangleGetsCapable(coreCount, memory, disk, din, dout);                                         //needs work
                String Schedule = createSCHDString(jobId, Integer.parseInt(value[1]), Integer.parseInt(value[2])); //the SCHD   //needs work
                dout.write(REDY.getBytes());
                reply = din.readLine();
                System.out.println("Server says: "+ reply);
            }
        } catch(IOException e) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
