public class Server {
    private String type;                // the server type   
    private int serverID;               // the server unquie ID
    private String state;               // server stat (inactive, booting, etc...)
    private int curStartTime;           // server start time
    private int core;                   // the number of cores on the server (capcity)
    private int mem;                    // the memory of the server
    private int disk;                   // server disk space


    public Server(String _type, int _serverID, String _state, int _curStartTime, int _core, int _mem, int _disk){
        setServerType(_type);                   // call the setter method for server type 
        setServerServerID(_serverID);           // call the setter method for serverID 
        setServerState(_state);                 // call the setter method for server state 
        setServerCurStartTime(_curStartTime);   // call the setter method for current start time 
        setServerCoreCount(_core);              // call the setter method for core
        setServerMemory(_mem);                  // call the setter method for memory 
        setServerDisk(_disk);                   // call the setter method for disk 
    }

    public void setServerType(String _type){    // setter method for server type 
        this.type = _type;
    }

    public void setServerServerID(int _serverID){   // setter method for serverID
        this.serverID = _serverID;
    }

    public void setServerState(String _state){      // setter method for server state
        this.state = _state;
    }

    public void setServerCurStartTime(int _curStartTime){   // setter method for current start time
        this.curStartTime = _curStartTime;
    }

    public void setServerCoreCount(int _coreCount){     // setter method for core 
        this.core = _coreCount;
    }

    public void setServerMemory(int _mem){      // setter method for memory
        this.mem = _mem;
    }

    public void setServerDisk(int _disk){       // setter method for disk
        this.disk = _disk;
    }




    public String getServerType(){          // getter method for server type
        return this.type;
    }

    public int getServerServerID(){          // getter method for serverID
        return this.serverID;
    }

    public String getServerState(){          // getter method for server stae
        return this.state;
    }

    public int getServerCurStartTime(){          // getter method for current start time
        return this.curStartTime;
    }

    public int getServerCoreCount(){          // getter method for server core count
        return this.core;
    }

    public int getServerMemory(){          // getter method for server memory 
        return this.mem;
    }

    public int getServerDisk(){          // getter method for server disk 
        return this.disk;
    }

    public int compareTo(Server c){                 //compare function used in the sorting of the server class
        if(this.core - c.core ==0){
            return c.type.compareTo(this.type);
        }
        return this.core - c.core;
    }

}
