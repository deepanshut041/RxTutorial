import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class RxjavaBasic {

    // Creating an Observable from an Existing Data Structure
    private static void create(List<String> list){
        // Observable<String>: represents a stream of data
        // Observable.fromIterable: creates a stream (Observable) from a collection
        Observable<String> stream = Observable.fromIterable(list);

        // from_observer.subscribe: declare an observer consuming the data passing in the streams.
        // None of the processing stages will be executed until you subscribe to it.
        stream.subscribe(
                s -> System.out.println("Stream Create : " + s),
                throwable -> System.out.println("Got an error"),
                () -> System.out.println("Stream Create: Completed")
        );
    }

    // Manipulate Streams
    private static void manipulate(List<String> list){
        Observable<String> stream = Observable.fromIterable(list);

        stream
            .map(String::toUpperCase) // map: for each item of the observed stream, apply the function
            .filter(name -> name.startsWith("A")) //filter: for each item of the observed stream (the uppercase names),
            .subscribe(
                s -> System.out.println("Stream Manipulate: " + s),
                throwable -> System.out.println("Got an error"),
                () -> System.out.println("Stream Manipulate: Completed")
            );
    }

    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        create(SUPER_HEROES);
        System.out.println();
        manipulate(SUPER_HEROES);
    }
}
