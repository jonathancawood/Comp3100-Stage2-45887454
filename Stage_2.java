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

    public static void sendMSG(String msg, DataOutputStream out) {                              // sends a given string to the iven dataoutputstream
		try {
			out.write(msg.getBytes());
			out.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

    public static String readMSG(BufferedReader in) throws IOException {                        // converts the received message from the server to a string
		String reply = in.readLine();
        System.out.println("Received");
        return reply;
	}

    public static String[] parsing(String data) {                                               // splits a given string in an array of strings, with the set delimiter "[ ]+"
		String delims = "[ ]+"; 
		String[] splitData = data.split(delims);
		return splitData;
	}

    private static void handshake(BufferedReader in, DataOutputStream out) {                    // when called perfrom the hand shake between the server and client
        try {
            String reply = new String();
            sendMSG(HELO, out);

            reply = readMSG(in);
			if (reply.equals(OK)) {                                                             // checks if reply is "OK" if not send error message, if so send "AUTH jono" message
				sendMSG(AUTH, out);
			} else {
				System.out.println(ERROR);
			}

            reply = readMSG(in);
			if (reply.equals(OK)) {                                                             // checks if the reply is "OK" if not send error message, if so send "REDY" message
				sendMSG(REDY, out);
			} else {
				System.out.println(ERROR);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
    }

    private static String createSCHDString(String jobId, int serverId, String serverType){      // create a SCHD string when given jobID, serverID and server type, and return the completed string
        return "SCHD" +WHITE_SPACE + jobId + WHITE_SPACE + serverType + WHITE_SPACE + serverId;
    }

    public static Server DescendingFirstFit(String[] job, Server[] serverlist){                 // newly desgined algorithm 

        int CoreCount = Integer.parseInt(job[2]);                                               // store the corecount from the passed job

        Collections.reverse(Arrays.asList(serverlist));                                         // reverse the order of the serverlist, this will put it in decending order

        Server DFFSERVER = new Server();                                                        // initialise DFFSERVER, this will be the server to return

        for (int i=0; i<serverlist.length; i++){                                                // loop throught the serverlist sequentially
            if (serverlist[i].getServerState() == DESIRED_SERVER_STATE){                        // check the server state, if the server state is equal to "idle" progress to next condition, if not check next server
                if (serverlist[i].getServerCoreCount() >= CoreCount){                           // check if server capacity is larger then the jobs corecount, if not check next server 
                    DFFSERVER = serverlist[i];                                                  // if passed store the current server
                    return DFFSERVER;                                                           // return the server to caller
                }
            }
        }   
    return DFFSERVER;                                                                           // if there is no server that meets this conditon send a blank server to caller 
    }

    public static void main(String[] args) {
        try { 
            System.out.println("Started");

			Socket s = new Socket(ADDRESS, PORT);                                               // open the socket on 127.0.0.1 and port 50000

			BufferedReader din = new BufferedReader(new InputStreamReader(s.getInputStream())); // declare a buffered reader
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());                  // declare a data outputstream 

            System.out.println("Connected");

            String rcvd = "";                                                                   // declare rcvd, which will be used to story the reply from the server
 
			handshake(din, dout);                                                               // call the handshake function

			rcvd = din.readLine();                                                              // read the reply, this is the first job to be scheduled
			String FirstJob = rcvd;                                                             // store the job in the first job variable

			sendMSG(GETS_ALL, dout);                                                            // sends gets all to the server to get all server infomation
			rcvd = din.readLine();                                                              // store the reply 
			String[] Data = parsing(rcvd);                                                      // call the parsing function on the recent reply
			sendMSG(OK, dout);                                                                  // reply back to the server with "OK"

			int numServer = Integer.parseInt(Data[1]);                                          // use the interget at data[1] to set the amount of server received
			Server[] serverList = new Server[numServer];                                        // create a server array, that is numservers big

			for (int i = 0; i < numServer; i++) {                                               // for every server parse the the server reply, then call the server class methods to create a server object for each
				rcvd = din.readLine();
				String[] stringList = parsing(rcvd);
				serverList[i] = new Server(stringList[0], Integer.parseInt(stringList[1]),stringList[2], Integer.parseInt(stringList[3]), Integer.parseInt(stringList[4]), Integer.parseInt(stringList[5]), Integer.parseInt(stringList[6]));
			}

            Arrays.sort(serverList);                                                            // sort the list of server in ascending order 

            sendMSG(OK, dout);                                                                  // reply to server with "OK" 
			rcvd = din.readLine();                          

			rcvd = FirstJob;                                                                    // set rcvd to the stored first job

			while (!rcvd.equals(NONE)) {                                                        // while the server reply is not "NONE"
				String[] job = parsing(rcvd);                                                   // split the joba into a string array
                Server SelectedServer = DescendingFirstFit(job, serverList);                    // call DFF with the job and the server list

                if (job[0] == "JOBN"){                                                          // if the job message starts with the String "JOBN" call the send message function with the createSCHD function, to send a job to the server
                    sendMSG(createSCHDString(job[2], SelectedServer.getServerServerID(), SelectedServer.getServerType()), dout);
                }else if (job[0] == "JCPL"){                                                    // if the job message starts with "JCPL" send redy to the server 
                    sendMSG(REDY, dout);
                }else if (job[0] == "OK"){                                                      // if the job message starts with "OK" send redy to the server 
				 	sendMSG(REDY, dout);
                }
				rcvd = din.readLine();                                                          // read the reply from the server, to then loop through or as many jobs
			}

			sendMSG(QUIT, dout);                                                                //send "QUIT" to the server

			dout.close();                                                                       // close the dataoutputstream 
			s.close();                                                                          // close the socket
            
        }catch(Exception e) {
            System.out.println(e);
        }

    }

}
