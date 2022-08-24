package prospects.checker.demo.validators.interfaces;

public interface Validator<T> {
    public T validate(String pin);
}
