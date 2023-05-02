import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Publisher {
    private Subject<double[]> subject = PublishSubject.create();

    public void publish(double[] message){
        subject.onNext(message);
    }

    public Observable<double[]> getObservable(){
        return subject;
    }
}
