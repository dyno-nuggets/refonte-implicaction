package com.dynonuggets.refonteimplicaction.core.rest.controller;

import com.dynonuggets.refonteimplicaction.auth.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.auth.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ControllerIntegrationTestBase {
    
    protected final Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected UserDetailsServiceImpl userDetailsService;

    @MockBean
    protected JwtProvider jwtProvider;

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
    }

    protected static String toJson(final Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}
