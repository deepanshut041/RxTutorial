import io.reactivex.Observable;

import java.util.Arrays;
import java.util.List;

public class RxJavaBasic4 {

    // takes the first item or last item, or emits the given default if the source stream is empty. This method returns a Single.
    private static void firstLast(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);
        Observable<String> stream2 = Observable.<String>empty();

        stream1.first("Default").subscribe(
            data -> System.out.println("[First 1] Received: " + data)
        );

        stream2.first("Empty Stream").subscribe(
            data -> System.out.println("[First 2] Received: " + data)
        );

        stream1.last("Default").subscribe(
            data -> System.out.println("[Last 1] Received: " + data)
        );

        stream2.last("Empty Stream").subscribe(
            data -> System.out.println("[Last 2] Received: " + data)
        );

    }

    // firstElement() forwards the first item from the observed stream. This method returns a Maybe as the observed stream can be empty.
    private static void firstLastElement(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);
        Observable<String> stream2 = Observable.<String>empty();

        stream1.firstElement().subscribe(
            data -> System.out.println("[First 1] Received: " + data)
        );

        stream2.firstElement().subscribe(
            data -> System.out.println("[First 2] Received: " + data),
            Throwable::printStackTrace,
            () -> System.out.println("[First 2] Empty Stream")
        );

        stream1.lastElement().subscribe(
            data -> System.out.println("[Last 1] Received: " + data)
        );

        stream2.lastElement().subscribe(
            data -> System.out.println("[Last 2] Received: " + data),
            Throwable::printStackTrace,
            () -> System.out.println("[Last 2] Empty Stream")
        );
    }

    // firstOrError() forwards the first item from the observed stream. If the observed stream is empty, it emits an error
    private static void firstLastorError(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);
        Observable<String> stream2 = Observable.<String>empty();

        stream1.firstOrError().subscribe(
                data -> System.out.println("[First 1] Received: " + data)
        );

        stream2.firstOrError().subscribe(
                data -> System.out.println("[First 2] Received: " + data),
                Throwable::printStackTrace
        );

        stream1.lastOrError().subscribe(
                data -> System.out.println("[Last 1] Received: " + data)
        );

        stream2.lastOrError().subscribe(
                data -> System.out.println("[Last 2] Received: " + data),
                Throwable::printStackTrace
        );
    }

    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        // firstLast(SUPER_HEROES);
        // firstLastElement(SUPER_HEROES);
        firstLastorError(SUPER_HEROES);
    }
}