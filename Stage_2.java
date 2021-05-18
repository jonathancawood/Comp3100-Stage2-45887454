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
    private static final String AUTH = "AUTH jono\n";
    private static final String ERROR = "ERROR Something has gone wrong!!!!";
    private static final String QUIT = "QUIT\n";
    private static final String DSSYSTEM_FILE_ADDRESs = "ds-system.xml";
    private static final String WHITE_SPACE = " ";
    private static final String GETS_ALL = "GETS All\n";
    private static final String GETS_CAPABLE = "GETS Capable";

    private static final String DESIRED_SERVER_STATE = "idle";
    
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
            sendMSG(HELO, dout);
            String reply = din.readLine();
			if (reply.equals(OK)) {
				sendMSG(AUTH, dout);
			} else {
				System.out.println(ERROR);
			}

			reply = din.readLine();
			if (reply.equals(OK)) {
				sendMSG(REDY, dout);
			} else {
				System.out.println(ERROR);
			}

		} catch (Exception e) {
			System.out.println(e);
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

    public static Server DecendingFirstFit(String[] job, Server[] serverlist){
        //the idea is to reverse the array and start with the biggest corecount first, first fit on steroids.
        
        //job is an array of strings, this array is created whith in the while loop
        //need to figure out what index the corecount is on to then determine which server is capable of running it 

        int CoreCount = Integer.parInt(job[2]); 

        Collections.sort(serverList, Collections.reverseOrder());

        Server DFFSERVER = Server("", 0, "", 0, 0, 0, 0);

        for (int i; i<serverList.size(); i++){
            if (serverList[i].getServerState() == DESIRED_SERVER_STATE){
                if (serverList[i].getServerCoreCount() >= CoreCount){
                    DFFSERVER = serverList[i];
                }
            }
        }
    return DFFSERVER;
    }

    public static void main(String[] args) {
        try { 
            socket = new Socket(ADDRESS,PORT);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            String rcvd = "";

			handshake(din, dout);

			rcvd = din.readLine();
			String FirstJob = rcvd;

			sendMSG(GETS_ALL, dout); 
			rcvd = din.readLine();
			String[] Data = parsing(rcvd); 
			sendMSG(OK, dout);

			int numServer = Integer.parseInt(Data[1]); 
			Server[] serverList = new Server[numServer]; 

			for (int i = 0; i < numServer; i++) {
				rcvd = din.readLine();
				String[] stringList = parsing(rcvd);
				serverList[i] = new Server(stringList[0], Integer.parseInt(stringList[1]),stringList[2], Integer.parseInt(stringList[3]), Integer.parseInt(stringList[4]), Integer.parseInt(stringList[5]), Integer.parseInt(stringList[6]));
			}

            Arrays.sort(serverList); 

            sendMSG(OK, dout);
			rcvd = din.readLine();

			rcvd = FirstJob;

			while (!rcvd.equals(NONE)) {
				String[] job = parsing(rcvd); 
                Server SelectedServer = DecendingFirstFit(FirstJob, serverList);

				switch (job[0]) {
				case "JOBN": // Schedule Job
                    sendMSG(createSCHDString(job[2], SelectedServer.getServerServerID(), SelectedServer.getServerType()), dout);
					break;
				case "JCPL": // If job is being completed send REDY
					sendMSG(REDY, dout);
					break;
				case "OK": // Ask for next job
					sendMSG(REDY, dout);
					break;
				}
				rcvd = din.readLine();
			}

			sendMSG(QUIT, dout);

			dout.close();
			socket.close();
            
        }catch(Exception e) {
            System.out.println(e);
        }

    }

}
