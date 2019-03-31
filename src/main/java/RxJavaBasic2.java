import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

//Observables and Subscribers
public class RxJavaBasic2 {

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // Ignore me.
        }
    }

    private static void sleep() {
        sleep(1000);
    }

    private static void dynamicStream(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // create method takes a method in a parameter called on every subscription (so for every subscriber).
        Observable<String> stream = Observable.create(s ->{
            boolean done = false;
            while (!done){
                String str = br.readLine();
                if(str.equals("")){ // Notify the completion
                    s.onComplete();
                    done = true;
                }
                else if(str.chars().allMatch( Character::isDigit )){ // Inject an error
                    s.onError(new Exception("String is invalid"));
                    done = true;
                }
                else
                    s.onNext(str); // Emit items
            }
        });

        stream.subscribe(
            i -> System.out.println("Received: " + i),
            err -> System.out.println("Boom"),
            () -> System.out.println("Completion")
        );
    }


    // Cold Observable: A cold stream restarts from the beginning for each subscriber, and every subscriber gets the full set of items.
    private static void coldStream(List<String> list){

        Observable<String> stream = Observable.fromIterable(list);

        stream
            .subscribe(
                    i -> System.out.println("[A] Received: " + i),
                    err -> System.out.println("[A] BOOM"),
                    () -> System.out.println("[A] Completion")
            );

        stream
            .subscribe(
                    i -> System.out.println("[B] Received: " + i),
                    err -> System.out.println("[B] BOOM"),
                    () -> System.out.println("[B] Completion")
            );
    }


    /*
    * Hot Observable: hot streams broadcast the same items to all listening subscribers.
    * However, if a subscriber arrives later, it won’t receive the previous items.
    * Logically, hot streams represent events or facts rather than known finite data sets.
    */
    private static void hotStream(){

        AtomicInteger counter = new AtomicInteger();

        Observable<Integer> stream = Observable.<Integer>create(
            s-> new Thread(
                    () -> {
                        while (counter.get() < 5) {
                            s.onNext(counter.getAndIncrement());
                            sleep();
                    }
            }).start())
            .publish().autoConnect();

        stream
            .subscribe(
                i -> System.out.println("[one] Received: " + i)
            );
        sleep();
        stream
            .subscribe(
                i -> System.out.println("[two] Received: " + i)
            );
    }


    /*
    * When the data is produced by the Observable itself, we call it a cold Observable.
    * When the data is produced outside the Observable, we call it a hot Observable.
    * ConnectableObservable helps us to convert a cold observable to hot observable.
    */
    private static void connectedStream(List<String> list){
        // ConnectableObservable helps us to convert a cold observable to hot observable.
        ConnectableObservable<String> stream = Observable.fromIterable(list).publish();

        stream
            .subscribe(
                    i -> System.out.println("[1] Received: " + i)
            );
        stream
            .subscribe(
                    i -> System.out.println("[2] Received: " + i)
            );
        stream.connect();
    }

    // Stopping emissions
    public static void stopEmissions(){
        AtomicInteger counter = new AtomicInteger();
        AtomicBoolean closeStream = new AtomicBoolean();
        Observable<Integer> stream = Observable.<Integer>create(
            s-> new Thread(
                () -> {
                    while (!closeStream.get()) {
                        s.onNext(counter.getAndIncrement() + 1);
                        sleep();
                    }
                }).start()
        ).publish()
        .autoConnect()
        .doOnDispose(()-> closeStream.set(true));

        Disposable d = stream.subscribe(
            s -> System.out.println("Received: " + s)
        );

        // Closes input after 10 second
        new Thread(
            () ->{
               sleep(10000);
               d.dispose();
            }
        ).start();
    }

    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        // coldStream(SUPER_HEROES);
        // connectedStream(SUPER_HEROES);
        // hotStream();
        stopEmissions();
    }
}
