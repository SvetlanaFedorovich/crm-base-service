package mvpproject.crmbaseservice.error.exception;

public enum CustomException {
    EMPTY_REQUEST_FIELD("Передано пустое поле запроса"),
    NOT_A_UNIQUE_BANK_ACCOUNT("Такой банковский счет уже существует!"),
    NOT_A_UNIQUE_CLIENT("Такой клиент уже существует"),
    CLIENT_NOT_FOUND("Клиент не найден"),
    INVALID_ID_TYPE_ENTERED("Введён неверный тип id");

    private final String message;

    CustomException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
