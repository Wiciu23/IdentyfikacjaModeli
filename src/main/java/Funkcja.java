import java.lang.annotation.Annotation;

abstract class Funkcja implements Functional{

    abstract double funkcja();

    @Override
    public double value() {
        return funkcja();
    }
}
