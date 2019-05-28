package sg.edu.rp.c347.p06_taskmanager_t2;

import java.io.Serializable;

public class Task implements Serializable{
    private int ID;
    private String name;
    private String Description;

    public Task(String name, String Description, int ID) {
        this.name = name;
        this.Description = Description;
        this.ID = ID;
    }

    public int getID() {
        return ID;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }




}
