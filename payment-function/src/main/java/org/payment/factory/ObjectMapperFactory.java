package org.payment.factory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperFactory {

    public static ObjectMapper createObjectMapper() {
        return new ObjectMapper().registerModule(new JavaTimeModule());
    }
}
