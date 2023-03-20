package RPQ;

public class Task {
    private final int id;
    private final int releaseTime;
    private final int executionTime;
    private final int deliveryTime;

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
