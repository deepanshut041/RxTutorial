import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

import java.util.Arrays;
import java.util.List;

public class RxJavaBasic6 {

    private static void toList(List<String> list){
        Observable<String> stream = Observable.fromIterable(list);

        stream
            .filter(name -> name.startsWith("A"))
            .toList() //creating a list of items)
            .subscribe(
                    list1 -> System.out.println("Collected " + list1.size() + " names")
            );

    }

    private static void flatMap(List<String> list){
        Observable<String> stream1 = Observable.fromIterable(list);
        Single<String> stream2 = Single.just("Sample");

        stream2.flatMapPublisher(s -> Flowable.fromArray(s.split(" ,"))).subscribe(
                data -> System.out.println("[First 1] Received: " + data)
        );
    }

    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        flatMap(SUPER_HEROES);
    }
}
