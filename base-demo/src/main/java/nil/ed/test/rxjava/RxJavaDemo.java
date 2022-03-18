package nil.ed.test.rxjava;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

/**
 * @author lidelin.
 */
public class RxJavaDemo {
    public static void main(String[] args) {
        Disposable disposable = Flowable.just("Hello world", "sads").subscribe(System.out::println);
        disposable.dispose();
    }
}
