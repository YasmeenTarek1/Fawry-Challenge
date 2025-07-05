import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Expirable {
    LocalDateTime getExpiryDate();

    default boolean isExpired() {
        return getExpiryDate().isBefore(LocalDateTime.now());
    }
}
