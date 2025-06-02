package org.example;

class Reservation {
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