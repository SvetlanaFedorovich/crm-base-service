package mvpproject.crmbaseservice.constant.errorMessage;

public enum ErrorMessage {
    SALES_NOT_FOUND("Продажа с id %s не найдена!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
