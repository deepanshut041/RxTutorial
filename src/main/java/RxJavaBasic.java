import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class RxJavaBasic {

    // Creating an Observable from an Existing Data Structure
    private static void create(List<String> list){
        // Observable<String>: represents a stream of data
        // Observable.fromIterable: creates a stream (Observable) from a collection
        Observable<String> stream = Observable.fromIterable(list);

        // from_observer.subscribe: declare an observer consuming the data passing in the streams.
        // None of the processing stages will be executed until you subscribe to it.
        stream.subscribe(
                s -> System.out.println("Stream Create : " + s), // onNext - this passes each item the observed stream is emitting
                throwable -> System.out.println("Got an error"), // onComplete - this communicates the end of the stream.
                () -> System.out.println("Stream Create: Completed") //onError - this communicates that something bad happened up the chain to the observer.
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

    // Producing Errors
    private static void produceError(List<String> list){
        Observable<String> stream = Observable.fromIterable(list);

        stream
            .map(name ->{
                if (name.endsWith("x")) {
                    throw new RuntimeException("What a terrible failure!");
                }
                return name.toUpperCase();
            })
            .subscribe(
                    s -> System.out.println("Stream: " + s),
                    throwable -> System.out.println("Got an error so terminating!!!!!!!!"),
                    () -> System.out.println("Stream: Completed")
            );
    }

    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        create(SUPER_HEROES);
        System.out.println();
        manipulate(SUPER_HEROES);
        System.out.println();
        produceError(SUPER_HEROES);
        System.out.println();
    }
}
