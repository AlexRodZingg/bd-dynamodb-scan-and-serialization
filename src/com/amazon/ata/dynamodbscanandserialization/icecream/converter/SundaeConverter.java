package com.amazon.ata.dynamodbscanandserialization.icecream.converter;

import com.amazon.ata.dynamodbscanandserialization.icecream.exception.SundaeSerializationException;
import com.amazon.ata.dynamodbscanandserialization.icecream.model.Sundae;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.platform.commons.util.StringUtils.isBlank;

public class SundaeConverter implements DynamoDBTypeConverter<String, List<Sundae>> {

    private ObjectMapper mapper;

    public SundaeConverter() {
        mapper = new ObjectMapper();
    }

    // Seriaelization
    @Override
    public String convert(List<Sundae> sundaes) {
        if (sundaes == null) {
            return "";
        }
        String jsonSundaes;

        try {
            jsonSundaes = mapper.writeValueAsString(sundaes);
        } catch (JsonProcessingException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }
        return jsonSundaes;
    }

    // Deserialization
    @Override
    public List<Sundae> unconvert(String s) {
        if (isBlank(s)) {
            return new ArrayList<Sundae>();
        }

        List<Sundae> sundaes;

        try {
            sundaes = mapper.readValue(s, new TypeReference<List<Sundae>>(){});
        } catch (IOException e) {
            throw new SundaeSerializationException(e.getMessage(), e);
        }

        return sundaes;
    }
}
