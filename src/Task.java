public class Task {
    private final int id;
    private final int releaseTime;
    private final int executionTime;

    public Task(int id, int releaseTime, int excutionTime) {
        this.id = id;
        this.releaseTime = releaseTime;
        this.executionTime = excutionTime;
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
                ", excutionTime=" + executionTime +
                '}';
    }
}
