import java.time.LocalDate;

public interface Expirable {
    LocalDate getExpiryDate();

    default boolean isExpired() {
        return getExpiryDate().isBefore(LocalDate.now());
    }
}
