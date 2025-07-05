import java.time.LocalDateTime;

public interface Expirable {
    LocalDateTime getExpiryDate();
    boolean isExpired();
    boolean isExpirable();
}
