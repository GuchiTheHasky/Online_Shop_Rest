package the.husky.onlineshoprest.exception.item;

public class ItemNotFoundException extends RuntimeException {
    public ItemNotFoundException(Long id) {
        super("Could not find item " + id);
    }
    public ItemNotFoundException(String message) {
        super(message);
    }
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
