package com.afklm.repind.msv.graphql.bff.example.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Business Error Message handler
 */
@Component
public class BusinessErrorMessageResolver extends DataFetcherExceptionResolverAdapter {

    /**
     * Business precondition status
     */
    private static final String PRECONDITION = "PRECONDITION_FAILED";
    /**
     * Bad request status
     */
    private static final String BAD_REQUEST = "BAD_REQUEST";

    /**
     * Resolve to single graphql error
     *
     * @param ex  throwable exception
     * @param env Data fetch environment
     * @return GraphQl error
     */
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env)
    {

        if (ex instanceof ServiceException serviceException)
        {
            return GraphqlErrorBuilder.newError(env)
                    .errorType(ErrorType.valueOf(handlePrecondition(serviceException.getStatus().name())))
                    .message(serviceException.getRestError().getDescription())
                    .extensions(Map.of("errorCode", serviceException.getRestError()))
                    .build();
        }
        return null;
    }

    /**
     * Handle precondition status with GraphQl error type
     *
     * @param name status name
     * @return status name
     */
    public String handlePrecondition(String name)
    {
        if (name.equals(PRECONDITION))
        {
            name = BAD_REQUEST;
        }
        return name;
    }
}
