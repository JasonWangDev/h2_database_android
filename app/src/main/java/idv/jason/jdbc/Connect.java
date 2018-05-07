package idv.jason.jdbc;

import java.util.Date;

public class Connect {

    private int id;
    private Date time;
    private String user;
    private String ip;


    public Connect(int id, Date time, String user, String ip) {
        this.id = id;
        this.time = time;
        this.user = user;
        this.ip = ip;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public String toString() {
        return "Connect{" +
                "id=" + id +
                ", time=" + time +
                ", user='" + user + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }

}
