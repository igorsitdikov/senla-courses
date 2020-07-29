package second;

public class Locker {
    private volatile Boolean flag = false;

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(final Boolean flag) {
        this.flag = flag;
    }
}
