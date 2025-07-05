import java.time.LocalDateTime;

public class noExpiryBehaviour implements Expirable {
    private final LocalDateTime expiryDate;

    public noExpiryBehaviour() {
        this.expiryDate = null;
    }

    @Override
    public boolean isExpirable() {
        return false;
    }

    public boolean isExpired() {
        return false;
    }

    @Override
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

}
