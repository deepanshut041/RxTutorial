import io.reactivex.Observable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

//Observables and Subscribers
public class RxJavaBasic2 {

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

    


    public static void main(String[] args) {
        List<String> SUPER_HEROES = Arrays.asList("Superman", "Batman", "Aquaman", "Asterix", "Captain America");
        // coldStream(SUPER_HEROES);
    }
}
