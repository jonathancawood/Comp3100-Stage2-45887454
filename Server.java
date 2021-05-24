public class Server {
    private String type;
    private int serverID;
    private String state;
    private int curStartTime;
    private int core;
    private int mem;
    private int disk;


    public Server(String _type, int _serverID, String _state, int _curStartTime, int _core, int _mem, int _disk){
        setServerType(_type);
        setServerServerID(_serverID);
        setServerState(_state);
        setServerCurStartTime(_curStartTime);
        setServerCoreCount(_core);
        setServerMemory(_mem);
        setServerDisk(_disk);

    }

    public void setServerType(String _type){
        this.type = _type;
    }

    public void setServerServerID(int _serverID){
        this.serverID = _serverID;
    }

    public void setServerState(String _state){
        this.state = _state;
    }

    public void setServerCurStartTime(int _curStartTime){
        this.curStartTime = _curStartTime;
    }

    public void setServerCoreCount(int _coreCount){
        this.core = _coreCount;
    }

    public void setServerMemory(int _mem){
        this.mem = _mem;
    }

    public void setServerDisk(int _disk){
        this.disk = _disk;
    }




    public String getServerType(){
        return this.type;
    }

    public int getServerServerID(){
        return this.serverID;
    }

    public String getServerState(){
        return this.state;
    }

    public int getServerCurStartTime(){
        return this.curStartTime;
    }

    public int getServerCoreCount(){
        return this.core;
    }

    public int getServerMemory(){
        return this.mem;
    }

    public int getServerDisk(){
        return this.disk;
    }

    public int compareTo(Server c){
        if(this.core - c.core ==0){
            return c.type.compareTo(this.type);
        }
        return this.core - c.core;
    }

}
