package com.techcallenge3.historicographql.config;

import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class GraphQLScalarConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(dateTimeScalar())
                .scalar(longScalar());
    }

    private GraphQLScalarType dateTimeScalar() {
        return GraphQLScalarType.newScalar()
                .name("DateTime")
                .description("Java LocalDateTime as scalar")
                .coercing(new Coercing<LocalDateTime, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof LocalDateTime) {
                            return ((LocalDateTime) dataFetcherResult).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        }
                        throw new CoercingSerializeException("Expected a LocalDateTime object.");
                    }

                    @Override
                    public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
                        try {
                            if (input instanceof String) {
                                return LocalDateTime.parse((String) input, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            }
                            throw new CoercingParseValueException("Expected a String");
                        } catch (Exception e) {
                            throw new CoercingParseValueException("Invalid DateTime format", e);
                        }
                    }

                    @Override
                    public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input instanceof StringValue) {
                            try {
                                return LocalDateTime.parse(((StringValue) input).getValue(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            } catch (Exception e) {
                                throw new CoercingParseLiteralException("Invalid DateTime format", e);
                            }
                        }
                        throw new CoercingParseLiteralException("Expected a StringValue");
                    }
                })
                .build();
    }

    private GraphQLScalarType longScalar() {
        return GraphQLScalarType.newScalar()
                .name("Long")
                .description("Java Long as scalar")
                .coercing(new Coercing<Long, Long>() {
                    @Override
                    public Long serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof Long) {
                            return (Long) dataFetcherResult;
                        }
                        if (dataFetcherResult instanceof Integer) {
                            return ((Integer) dataFetcherResult).longValue();
                        }
                        throw new CoercingSerializeException("Expected a Long or Integer object.");
                    }

                    @Override
                    public Long parseValue(Object input) throws CoercingParseValueException {
                        try {
                            if (input instanceof Long) {
                                return (Long) input;
                            }
                            if (input instanceof Integer) {
                                return ((Integer) input).longValue();
                            }
                            if (input instanceof String) {
                                return Long.parseLong((String) input);
                            }
                            throw new CoercingParseValueException("Expected a Long, Integer or String");
                        } catch (NumberFormatException e) {
                            throw new CoercingParseValueException("Invalid Long format", e);
                        }
                    }

                    @Override
                    public Long parseLiteral(Object input) throws CoercingParseLiteralException {
                        try {
                            if (input instanceof graphql.language.IntValue) {
                                return ((graphql.language.IntValue) input).getValue().longValue();
                            }
                            if (input instanceof StringValue) {
                                return Long.parseLong(((StringValue) input).getValue());
                            }
                            throw new CoercingParseLiteralException("Expected an IntValue or StringValue");
                        } catch (NumberFormatException e) {
                            throw new CoercingParseLiteralException("Invalid Long format", e);
                        }
                    }
                })
                .build();
    }
}

