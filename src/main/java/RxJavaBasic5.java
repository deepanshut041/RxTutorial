import io.reactivex.Observable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

// Skip and Take
public class RxJavaBasic5 {

    private static void skipTake(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);

        stream1
        .skip(2) // skip operator creates a stream that skips the first items emitted by the observed stream and emits the remainder.
        .take(2) // take operator creates a stream which emits only the first items emitted by the observed stream.
        .subscribe(
                data -> System.out.println("[First 1] Received: " + data)
        );
    }

    // when it realize that your stream is empty, it provide a default stream
    private static void switchIfEmpty(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);
        Observable<String> stream2 = Observable.<String>empty();

        stream2
            .switchIfEmpty(stream1)
            .subscribe(
                    data -> System.out.println("[First 1] Received: " + data)
            );
    }

    // when it realize that your stream is empty, it provide a default input
    private static void defaultIfEmpty(){
        Observable<String> stream2 = Observable.<String>empty();

        stream2.defaultIfEmpty("Oh no... super hero doesn't exist")
                .subscribe(
                        data -> System.out.println("[First 1] Received: " + data)
                );
    }

    // scan emits all computed values, including the seed (initial (seed), initial + 0, initial + 0 + 1, .......​).
    private static void scan(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);

        stream1
            .scan("We are adding", (last_result, item) -> last_result + " " + item)
                .subscribe(
                        data -> System.out.println("[First 1] Received: " + data)
                );
    }

    // scan emits all computed values, including the seed (initial (seed), initial + 0, initial + 0 + 1, .......​).
    private static void reduce(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);

        stream1
                .reduce("We are adding", (last_result, item) -> last_result + " " + item)
                .subscribe(
                        data -> System.out.println("[First 1] Received: " + data)
                );
    }

    // reduce is emitting a single value when the observed streams are completed(initial + 0 + 1 + 2 + 3.......)
    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        // skipTake(SUPER_HEROES);
        // switchIfEmpty(SUPER_HEROES);
        // defaultIfEmpty();
        // scan(SUPER_HEROES);
        // reduce(SUPER_HEROES);
    }
}
