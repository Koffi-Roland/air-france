package com.afklm.repind.msv.graphql.bff.example.controller;

import com.afklm.repind.msv.graphql.bff.example.client.LastActivityClient;
import com.afklm.repind.msv.graphql.bff.example.exception.ServiceException;
import com.afklm.repind.msv.graphql.bff.example.model.EmailResponse;
import com.afklm.repind.msv.graphql.bff.example.model.LastActivity;
import com.afklm.repind.msv.graphql.bff.example.client.EmailClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GraphQlController {

    /**
     * Last activity client - inject by spring
     */
    private final LastActivityClient lastActivityClient;
    /**
     * Email client - inject by spring
     */
    private final EmailClient emailClient;

    /**
     * Get emails according to the given gin
     *
     * @param gin individual number
     * @return list of email
     */
    @QueryMapping
    public List<EmailResponse> getEmailsByGin(@Argument String gin) throws ServiceException, IOException
    {
        return this.emailClient.getEmailsByGin(gin);
    }

    /**
     * Get last activity according to the given gin
     *
     * @param gin individual number
     * @return LastActivityModel last activity model
     */
    @SchemaMapping(typeName = "Query")
    public LastActivity getLastActivityByGin(@Argument String gin) throws ServiceException, IOException
    {
        return this.lastActivityClient.getLastActivityByGin(gin);
    }

}
