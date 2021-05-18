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
    private static final String QUIT = "QUIT\n";
    private static final String DSSYSTEM_FILE_ADDRESs = "ds-system.xml";
    private static final String WHITE_SPACE = " ";
    private static final String GETS_ALL = "GETS All\n";
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
    }   //not used

    private static List<String> parseJOBNMessage(String serverReply){
        List<String> info = new ArrayList<>();
        String[] splitInfo = serverReply.split(WHITE_SPACE);
        info.add(splitInfo[2]);
        info.add(splitInfo[4]);
        info.add(splitInfo[5]);
        info.add(splitInfo[6]);
        return info;
    }   //not used (implment to convert string to job class, maybe store in a list)

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
    }   // not used (using gets all)

    public static Server NewAlgorithm(Job job, String[] serverlist){
        //
        //
        //
        //
        //
        //
    return NewAlgorithmSERVER; // output of the new algorithm
    }

    public static void main(String[] args) {
        try { 
            socket = new Socket(ADDRESS,PORT);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            String rcvd = "";

			HandShake(din, dout);

			rcvd = readMSG(din);
			String firstjob = rcvd;

			sendMSG(GETS_ALL, dout); 
			rcvd = readMSG(din);
			String[] Data = parsing(rcvd); 
			sendMSG(OK, dout);

			int numServer = Integer.parseInt(Data[1]); 
			Server[] serverList = new Server[numServer]; 

			for (int i = 0; i < numServer; i++) {
				rcvd = readMSG(din);
				String[] stringList = parsing(rcvd);
				serverList[i] = new Server(stringList[0], stringList[1], stringList[2], stringList[3], stringList[4], stringList[5]);
			}

            Arrays.sort(serverList); 

            //
            //
            //
            
            Server SelectedServer = NewAlgorithm(Firstjob, serverList);
            // thinking this will have to go in the while loop so that ever job gets the right server
            // going to have to figure out how to implement this in the while loop 
            

            
            //
            //
            //

            sendMSG(OK, dout); // catch the "." at end of data stream.
			rcvd = readMSG(din);

			// Schedule jobs to server
			rcvd = firstjob;

			while (!rcvd.equals(NONE)) {
				String[] job = parsing(rcvd); // Get job id and job type for switch statement

                //
                //

                //thinking the code to call the algorithm is going to have to be here. 

                //
                //

				switch (job[0]) {
				case "JOBN": // Schedule job
                    sendMSG(createSCHDString(job[2], SelectedServer.getServerType(), SelectedServer.getType()), dout);
					break;
				case "JCPL": // If job is being completed send REDY
					sendMSG(REDY, dout);
					break;
				case "OK": // Ask for next job
					sendMSG(REDY, dout);
					break;
				}
				rcvd = readMSG(din);
			}

			sendMSG(QUIT, dout);

			dout.close();
			s.close();
            
        }catch(IOException e) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
