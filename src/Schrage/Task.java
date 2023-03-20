package Schrage;

public class Task {
    private int id;
    private int releaseTime;
    private int executionTime;
    private  int deliveryTime;

    public void setId(int id) {
        this.id = id;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Task(int id, int releaseTime, int executionTime, int deliveryTime) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.executionTime = executionTime;
        this.deliveryTime = deliveryTime;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public int getExcutionTime() {
        return executionTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", releaseTime=" + releaseTime +
                ", executionTime=" + executionTime +
                ", deliveryTime=" + deliveryTime +
                '}';
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }
}
