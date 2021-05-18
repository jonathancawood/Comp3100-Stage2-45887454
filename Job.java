public class Job {
    private int submitTime;
    private int jobID;
    private int estRuntime;
    private int core;
    private int memory;
    private int disk;

    public Job(int _submitTime, int _jobID, int _estRuntime, int _core, int _memory, int _disk){
        //setters methods
        setJOBsubmitTime(_submitTime);
        setJOBjobID(_jobID);
        setJOBestRuntime(_estRuntime);
        setJOBcore(_core);
        setJOBmemory(_memory);
        setJOBdisk(_disk);
    }

    public void setJOBsubmitTime(int _submitTime){
        this.submitTime = _submitTime;
    }

    public void setJOBjobID(int _jobID){
        this.jobID = _jobID;
    }

    public void setJOBestRuntime(int _estRuntime){
        this.estRuntime = _estRuntime;
    }

    public void setJOBcore(String _core){
        this.core = _core;
    }

    public void setJOBmemory(int _memory){
        this.memory = _memory;
    }
    
    public void setJOBdisk(int _disk){
        this.disk = _disk;
    }



    public int getJOBsubmitTime(){
        return this.submitTime;
    }

    public int getJOBjobID(){
        return this.jobID;
    }

    public int getJOBestRuntime(){
        return this.estRuntime;
    }

    public int getJOBcore(){
        return this.core;
    }

    public int getJOBmemory(){
        return this.memory;
    }

    public int getJOBdisk(){
        return this.disk;
    }

}