// This class is not used in the Stage_2 implementation but was created, in future implementation this Job class will be useful.

public class Job {
    private int submitTime;     // the job submit time
    private int jobID;          // the jobID
    private int estRuntime;     // the estimated run time of the job
    private int core;           // the core count of the job 
    private int memory;         // the memory required
    private int disk;           // the disk space

    public Job(int _submitTime, int _jobID, int _estRuntime, int _core, int _memory, int _disk){
        setJOBsubmitTime(_submitTime);         // call the setter method for submit time
        setJOBjobID(_jobID);                   // call the setter method for job id 
        setJOBestRuntime(_estRuntime);         // call the setter method for for estimated run time
        setJOBcore(_core);                     // call the setter method for core
        setJOBmemory(_memory);                 // call the setter method for memory
        setJOBdisk(_disk);                     // call the setter method for disk 
    }

    public void setJOBsubmitTime(int _submitTime){    // setter method for job submit time 
        this.submitTime = _submitTime;
    }

    public void setJOBjobID(int _jobID){    // setter method for jobID
        this.jobID = _jobID;
    }

    public void setJOBestRuntime(int _estRuntime){    // setter method for job estimated run time 
        this.estRuntime = _estRuntime;
    }

    public void setJOBcore(String _core){    // setter method for job cores 
        this.core = _core;
    }

    public void setJOBmemory(int _memory){    // setter method for job memory
        this.memory = _memory;
    }
    
    public void setJOBdisk(int _disk){    // setter method for job disk
        this.disk = _disk;
    }



    public int getJOBsubmitTime(){          // getter method for job submit time
        return this.submitTime;
    }

    public int getJOBjobID(){          // getter method for job jobID
        return this.jobID;
    }

    public int getJOBestRuntime(){          // getter method for job run time
        return this.estRuntime;
    }

    public int getJOBcore(){          // getter method for job core
        return this.core;
    }

    public int getJOBmemory(){          // getter method for job memory
        return this.memory;
    }

    public int getJOBdisk(){          // getter method for job disk
        return this.disk;
    }

}
