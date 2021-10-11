package com.tinkoff.edu.app.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class FileLoanCalcRepository implements LoanCalcRepository {
    private Path fileStorage = Path.of("data.txt");
    private HashMap<UUID, LoanResponse> storage = readStorageFromFile();

    @Override
    public LoanResponse get(UUID requestId) throws LoanBusinessException {
        if (!storage.containsKey(requestId)) {
            throw new LoanBusinessException("Не найдена запись по " + requestId);
        }
        return storage.get(requestId);
    }

    @Override
    public LoanResponse update(LoanResponse response) {
        if (response == null) {
            throw new LoanBusinessException("Передан пустой ответ");
        }
        if (response.getRequestId() == null) {
            throw new LoanBusinessException("Передан пустой идентификатор");
        }
        storage.put(response.getRequestId(), response);
        try {
            writeStorageToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public LoanResponse save(LoanResponse response) throws LoanBusinessException {
        if (response == null) {
            throw new LoanBusinessException("Передан пустой ответ");
        }
        if (response.getRequestId() == null) {
            throw new LoanBusinessException("Передан пустой идентификатор");
        }
        if (storage.containsKey(response.getRequestId())) {
            throw new LoanBusinessException("Ответ с таким идентификатором уже существует");
        }
        storage.put(response.getRequestId(), response);
        try {
            writeStorageToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public void clean() {
        storage.clear();
        try {
            writeStorageToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<LoanResponse> getLoanResponsesByLoanType(LoanType loanType) {
        if (loanType == null) {
            throw new LoanBusinessException("Некорректный тип клиента");
        }
        List<LoanResponse> loanResponses = storage.values().stream()
                .filter(response -> response.getRequest().getType() == loanType)
                .collect(Collectors.toList());
        return loanResponses;
    }

    public int getLoanResponsesAmountSumByLoanType(LoanType loanType) {
        if (loanType == null) {
            throw new LoanBusinessException("Некорректный тип клиента");
        }
        int sum = storage.values().stream()
                .filter(response -> response.getRequest().getType() == loanType)
                .mapToInt(response -> response.getRequest().getAmount())
                .sum();
        return sum;
    }

    private HashMap<UUID, LoanResponse> readStorageFromFile() {
        List<String> stringList;
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<LoanResponse> typeRef = new TypeReference<>() {
        };
        try {
            stringList = Files.readAllLines(fileStorage);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }

        List<LoanResponse> loanResponses = stringList.stream()
                .filter(stringListItem -> !stringListItem.equals(""))
                .map(stringListItem -> {
                    try {
                        return objectMapper.readValue(stringListItem, typeRef);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        HashMap<UUID, LoanResponse> result = new HashMap<>();

        loanResponses.forEach(response -> result.put(response.getRequestId(), response));

        return result;
    }

    private void writeStorageToFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        List<String> stringList = storage.values().stream()
                .map(response -> {
                    try {
                        return objectMapper.writeValueAsString(response);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .collect(Collectors.toList());
        Files.write(fileStorage, stringList, TRUNCATE_EXISTING, CREATE);
    }
}
