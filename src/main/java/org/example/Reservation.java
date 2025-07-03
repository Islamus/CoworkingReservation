package org.example;

public class Reservation {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    String id;
    String userName;
    String workspaceId;
    String date;
    String startTime;
    String endTime;

    @Override
    public String toString() {
        return "Reservation " +
                "workspaceId: '" + workspaceId +
                ", userName: '" + userName +
                " date: '" + date  +
                ", startTime: '" + startTime +
                ", endTime: '" + endTime
                ;
    }

    public Reservation(String id, String userName, String workspaceId, String date, String startTime, String endTime) {
        this.id = id;
        this.userName = userName;
        this.workspaceId = workspaceId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    public String toFileString() {
        return id + "," + userName + "," + "," + date + "," + startTime + "," + endTime;
    }
}