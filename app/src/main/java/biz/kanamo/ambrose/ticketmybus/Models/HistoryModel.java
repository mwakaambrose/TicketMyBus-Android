package biz.kanamo.ambrose.ticketmybus.Models;

/**
 * Created by ambrose on 3/17/17.
 */

public class HistoryModel {
    private String id;
    private String to_from;
    private String time;
    private String name;

    public HistoryModel(String id, String to_from, String time, String name) {
        this.id = id;
        this.to_from = to_from;
        this.time = time;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTo_from() {
        return to_from;
    }

    public void setTo_from(String to_from) {
        this.to_from = to_from;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
