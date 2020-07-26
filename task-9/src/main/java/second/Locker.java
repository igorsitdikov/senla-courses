package second;

public class Locker {
    private volatile int flag = 1;

    public int getFlag() {
        return flag;
    }

    public void setFlag(final int flag) {
        this.flag = flag;
    }
}
