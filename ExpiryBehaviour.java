import java.time.LocalDateTime;

public class ExpiryBehaviour implements Expirable {
    private LocalDateTime expiryDate;

    public ExpiryBehaviour(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean isExpirable() {
        return true;
    }

    @Override
    public boolean isExpired() {
        return getExpiryDate().isBefore(LocalDateTime.now());
    }

    @Override
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
}