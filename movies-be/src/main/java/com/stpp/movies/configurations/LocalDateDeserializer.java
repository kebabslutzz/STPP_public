package com.stpp.movies.configurations;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final Logger LOGGER = Logger.getLogger(LocalDateDeserializer.class.getName());

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getValueAsString();
        LOGGER.info("Deserializing date: " + dateStr);
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        LOGGER.info("Deserialized date: " + date);
        return date;
    }
}