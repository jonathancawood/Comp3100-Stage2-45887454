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

    public static void sendMSG(String msg, DataOutputStream out) {
		try {
			out.write(msg.getBytes());
			out.flush();
		} catch (IOException e) {
			System.out.println(e);
		}

	}

    public static String[] parsing(String data) {
		String delims = "[ ]+"; 
		String[] splitData = data.split(delims);
		return splitData;
	}

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

    private static List<Server> parseDSSystemXML(String fileAddress){
        List<Server> dsServerList = new ArrayList<>(); 



        	// for (int i = 0; i < numServer; i++) {
			// 	rcvd = readMSG(din);
			// 	String[] stringList = parsing(rcvd);
			// 	serverList[i] = new Server(stringList[0], stringList[4]);
			// }
        




        
        
        
        //create a Server class
        //do the parsing here
        //create job class, complete it with the reqired attributes like getter and setter functions
        //create Server class, complete it with the required attributes like getter and setter functions 
        //optional >> implement comparable for server class or job class
        //easily sort those classes based on any attributes you want based collections java class
        return dsServerList;
    }

    private static List<String> parseJOBNMessage(String serverReply){
        List<String> info = new ArrayList<>();
        String[] splitInfo = serverReply.split(WHITE_SPACE);
        info.add(splitInfo[2]);
        info.add(splitInfo[4]);
        info.add(splitInfo[5]);
        info.add(splitInfo[6]);
        return info;
    }

    private static String createSCHDString(String jobId, int serverId, String serverType){
        return "SCHD" +WHITE_SPACE + jobId + WHITE_SPACE + serverType + WHITE_SPACE + serverId;
    }

    private static String[] hangleGetsCapable(int coreCount, int memory, int disk, DataInputStream din, DataOutputStream dout){
        String GetsCapable = GETS_CAPABLE +WHITE_SPACE+ coreCount +WHITE_SPACE+ memory +WHITE_SPACE+ disk;
        dout.write(GetsCapable.getBytes());
        String reply = din.readLine();
        String[] datareply =reply.split(WHITE_SPACE);
        int lines = Integer.parseInt(datareply[1]);
        int length = Integer.parseInt(datareply[2]);
        for (){                                                                                      //needs work
            //read message from server                                                               //needs work
            //parse and add it to the temporay servers                                               //needs work 
        }                                                                                            //needs work
        //look for the best capcable server within temporry servers.                                 //needs work
        String sId, stype;
        String[] ans = new String[2];
        ans[0]= sId;
        ans[1]= stype;
        return ans;
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






            // sendMSG("GETS All\n", dout);                                                    // get server DATA
			// rcvd = readMSG(din);
			// String[] Data = parsing(rcvd);                                                  // Data is an array of strings of all the servers
			// sendMSG("OK\n", dout);

			// // Initialise variable for server DATA
			// int numServer = Integer.parseInt(Data[1]);                                      // Number of servers on system.
			// Server[] serverList = new Server[numServer];                                    // Create server array.

			// // Loop through all servers to create server list
			// for (int i = 0; i < numServer; i++) {
			// 	rcvd = readMSG(din);
			// 	String[] stringList = parsing(rcvd);
			// 	serverList[i] = new Server(stringList[0], stringList[4]);
			// }

			// Arrays.sort(serverList); // Sort Servers




            // //ds-system.xml is available 
            // List<Server> dsServers = parseDSSystemXML(ADDRESS);
            // // to find out the server at the ds-sim server sde,
            // dout.write(REDY.getBytes());
            // String reply = din.readLine();
            // System.out.println("Server says: "+reply);
            // while(!reply.equals(NONE)){
            //     //parse the incoming message from ds-server
            //     List<String> parsedInfo = parseJOBNMessage(reply);                  //needs work 
            //     //parseInfo 0> jobId
            //     //1>> core
            //     //2>> mem
            //     //3>> disk 

            //     String[] value = hangleGetsCapable(coreCount, memory, disk, din, dout);                                         //needs work
            //     String Schedule = createSCHDString(jobId, Integer.parseInt(value[1]), Integer.parseInt(value[2])); //the SCHD   //needs work
            //     dout.write(REDY.getBytes());
            //     reply = din.readLine();
            //     System.out.println("Server says: "+ reply);
            }
        } catch(IOException e) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
