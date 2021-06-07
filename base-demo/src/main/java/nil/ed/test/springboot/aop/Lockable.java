package nil.ed.test.springboot.aop;

public interface Lockable {
    void lock();
    void unlock();
    boolean locked();
}