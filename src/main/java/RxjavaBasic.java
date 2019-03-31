import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.Collections;
import java.util.List;

public class RxjavaBasic {

    // Creating an Observable from an Existing Data Structure
    private static void create(){
        List<String> list = Collections.nCopies(5, "Hello");

        // Observable<String>: represents a stream of data
        // Observable.fromIterable: creates a stream (Observable) from a collection
        Observable<String> stream = Observable.fromIterable(list);

        // from_observer.subscribe: declare an observer consuming the data passing in the streams.
        // None of the processing stages will be executed until you subscribe to it.

        stream.subscribe(
                s -> System.out.println("Stream: " + s),
                throwable -> System.out.println("Got an error"),
                () -> System.out.println("Stream: Observation completed")
        );
    }



    public static void main(String[] args) {
        create();
    }
}
