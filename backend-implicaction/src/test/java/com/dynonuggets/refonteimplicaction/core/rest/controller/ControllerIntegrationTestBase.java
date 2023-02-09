package com.dynonuggets.refonteimplicaction.core.rest.controller;

import com.dynonuggets.refonteimplicaction.auth.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.auth.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

public class ControllerIntegrationTestBase {
    protected static final String TOTAL_ELEMENTS_PATH = "$.totalElements";
    protected static final String TOTAL_PAGES_PATH = "$.totalPages";

    protected static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);
    protected final Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected UserDetailsServiceImpl userDetailsService;

    @MockBean
    protected JwtProvider jwtProvider;

    private static final ObjectMapper mapper = new ObjectMapper();

    protected static String toJson(final Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }
}
