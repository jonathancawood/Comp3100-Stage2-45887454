import java.io.*;
import java.net.*;
import java.util.*;

public class Stage_2 {
    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 50000;
    private static final String REDY = "REDY\n";
    private static final String HELO = "HELO";
    private static final String OK = "OK";
    private static final String NONE = "NONE\n";
    private static final String AUTH = "AUTH jono";
    private static final String ERROR = "ERROR Something has gone wrong!!!!";
    private static final String QUIT = "QUIT\n";
    private static final String WHITE_SPACE = " ";
    private static final String GETS_ALL = "GETS All\n";
    private static final String DESIRED_SERVER_STATE = "idle";

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

    private static void handshake(BufferedReader in, DataOutputStream out) {
        try {
            sendMSG(HELO, out);
            String reply = in.readLine();
			if (reply.equals(OK)) {
				sendMSG(AUTH, out);
			} else {
				System.out.println(ERROR);
			}

			reply = in.readLine();
			if (reply.equals(OK)) {
				sendMSG(REDY, out);
			} else {
				System.out.println(ERROR);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
    }

    private static String createSCHDString(String jobId, int serverId, String serverType){
        return "SCHD" +WHITE_SPACE + jobId + WHITE_SPACE + serverType + WHITE_SPACE + serverId;
    }

    public static Server DescendingFirstFit(String[] job, Server[] serverlist){
        //the idea is to reverse the array and start with the biggest corecount first, first fit on steroids.
        
        //job is an array of strings, this array is created whith in the while loop
        //need to figure out what index the corecount is on to then determine which server is capable of running it 

        int CoreCount = Integer.parseInt(job[2]); 

        Collections.reverse(Arrays.asList(serverlist));

        Server DFFSERVER = new Server("", 0, "", 0, 0, 0, 0);

        for (int i=0; i<serverlist.length; i++){
            if (serverlist[i].getServerState() == DESIRED_SERVER_STATE){
                if (serverlist[i].getServerCoreCount() >= CoreCount){
                    DFFSERVER = serverlist[i];
                }
            }
        }
    return DFFSERVER;
    }

    public static void main(String[] args) {
        try { 
            System.out.println("Started");

			Socket s = new Socket(ADDRESS, PORT);

			BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream()));
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            System.out.println("Connected");

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
                Server SelectedServer = DescendingFirstFit(job, serverList);

                if (job[0] == "JOBN"){
                    sendMSG(createSCHDString(job[2], SelectedServer.getServerServerID(), SelectedServer.getServerType()), dout);
                }else if (job[0] == "JCPL"){
                    sendMSG(REDY, dout);
                }else if (job[0] == "OK"){
				 	sendMSG(REDY, dout);
                }
				rcvd = din.readLine();
			}

			sendMSG(QUIT, dout);

			dout.close();
			s.close();
            
        }catch(Exception e) {
            System.out.println(e);
        }

    }

}
