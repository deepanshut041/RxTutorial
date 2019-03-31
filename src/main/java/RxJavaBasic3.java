import io.reactivex.*;
import io.reactivex.schedulers.Schedulers;

// Single, Completable, Maybe and Flowable
public class RxJavaBasic3 {

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

    /*
     * A Single is a specialized stream that only emits one item.
     * next and complete are replaced by success.
     * Singles are often used for asynchronous operations returning a single result, such as an HTTP request.
     */
    private static void single(){
        Single<String> stream = Single.just("Single Object");

        stream.subscribe(
            name -> System.out.println("Received: " + name) // Success
            ,err-> System.out.println("Got an error") // error
        );
    }

    // Maybe is a stream that can emit 0 or 1 item. It is useful because Single can’t emit null. Maybe is often used for methods that may return null.
    private static void maybe(){
        Maybe<String> stream1 = Maybe.just("Maybe Object");
        Maybe<String> stream2 = Maybe.empty();

        stream1.subscribe(
            name -> System.out.println("Stream [1]: Received - " + name) // Success
            ,err-> System.out.println("Stream [1]: Got an error") // error
            ,() -> System.out.println("Stream [1]: Not Recived any thing") // when the stream complete, without a value
        );

        stream2.subscribe(
            name -> System.out.println("Stream [2]: Received - " + name) // Success
            ,err-> System.out.println("Stream [2]: Got an error") // error
            ,() -> System.out.println("Stream [2]: Not Received any thing") // complete value = null
        );

    }

    // Completable represents a stream not emitting a value but simply concerned with an action being executed.
    private static void completable(){
        Completable stream = Completable.fromAction(() -> System.out.println("Hello"));

        stream.subscribe(
            () -> System.out.println("Task Completed") // Success
            ,err-> System.out.println("Got an error") // error
        );
    }


    /*
    * If your consumer cannot keep up with the pace, something bad is going to happen.
    * This is where back-pressure comes into the picture.
    * The emissions of the numbers are too fast for the consumer, and because the emissions are being pushed into an unbounded buffer by observeOn,
    * this can be the source of many problems such as…​ running out of memory.
    * Flowable is like Observable (it may contain multiple items) but implements a back-pressure protocol.
    * This protocol tells the source stream to emit items at a pace specified by the consumer.
    */
    private static void flowable(){
        // Create an observable emitting all numbers between 1 and 999_999_999
        Flowable.range(1, 1000)
                .map(Item::new)
                // Emissions are made on the caller thread (main)
                // The next processing stages and the terminal subscriber
                // is now called on a separate thread (io thread).
                .observeOn(Schedulers.io())
                .subscribe(
                    item -> {
                        nap();
                        System.out.println("Received : " + item.i);
                    }
                );

        // Wait for 20 mili seconds. Without this the process will terminate immediately.
        sleep(20);

    }

    private static void nap() {
        new Thread(()->{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static class Item {
        private final int i;

        Item(int number) {
            System.out.println("Constructing item using " + number);
            this.i = number;
        }
    }


    public static void main(String[] args) {
        System.out.println();
        // single();
        // maybe();
        // completable();
        flowable();
    }
}
