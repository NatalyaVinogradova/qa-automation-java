package com.tinkoff.edu.app.models;

import java.util.UUID;

public class EntryNotFoundException extends RuntimeException{
    public EntryNotFoundException(String message) {
        super(message);
    }

    public EntryNotFoundException(UUID requestId) {
        super("Запись с идентификатором " + requestId + " не найдена.");
    }
}
