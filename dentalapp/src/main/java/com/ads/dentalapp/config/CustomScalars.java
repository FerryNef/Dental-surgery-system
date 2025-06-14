package com.ads.dentalapp.config;

import graphql.language.IntValue;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.GraphQLScalarType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CustomScalars {

    // LocalDate scalar
    public static GraphQLScalarType createLocalDateScalar() {
        return GraphQLScalarType.newScalar()
                .name("LocalDate")
                .coercing(new Coercing<LocalDate, String>() {

                    @Override
                    public String serialize(Object input) {
                        if (input instanceof LocalDate) {
                            return ((LocalDate) input).format(DateTimeFormatter.ISO_LOCAL_DATE);
                        }
                        return null;
                    }

                    @Override
                    public LocalDate parseValue(Object input) {
                        return LocalDate.parse((String) input, DateTimeFormatter.ISO_LOCAL_DATE);
                    }

                    @Override
                    public LocalDate parseLiteral(Object input) {
                        return LocalDate.parse((String) input, DateTimeFormatter.ISO_LOCAL_DATE);
                    }
                })
                .build();
    }

    // LocalDateTime scalar
    public static GraphQLScalarType createLocalTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("LocalTime")
                .coercing(new Coercing<LocalTime, String>() {

                    @Override
                    public String serialize(Object input) {
                        if (input instanceof LocalTime) {
                            return ((LocalTime) input).format(DateTimeFormatter.ISO_LOCAL_TIME);
                        }
                        return null;
                    }

                    @Override
                    public LocalTime parseValue(Object input) {
                        return LocalTime.parse((String) input, DateTimeFormatter.ISO_LOCAL_TIME);
                    }

                    @Override
                    public LocalTime parseLiteral(Object input) {
                        return LocalTime.parse((String) input, DateTimeFormatter.ISO_LOCAL_TIME);
                    }
                })
                .build();
    }

    public static GraphQLScalarType createLongScalar() {
        return GraphQLScalarType.newScalar()
                .name("Long")
                .coercing(new Coercing<Long, String>() {

                    @Override
                    public String serialize(Object input) {
                        if (input instanceof Long) {
                            return input.toString();
                        }
                        return null;
                    }

                    @Override
                    public Long parseValue(Object input) {
                        return Long.valueOf(input.toString());
                    }

                    @Override
                    public Long parseLiteral(Object input) {
                        if (input instanceof IntValue) {
                            return ((IntValue) input).getValue().longValue();
                        }
                        throw new CoercingParseLiteralException("Expected IntValue for Long scalar");
                    }
                })
                .build();
    }
}
