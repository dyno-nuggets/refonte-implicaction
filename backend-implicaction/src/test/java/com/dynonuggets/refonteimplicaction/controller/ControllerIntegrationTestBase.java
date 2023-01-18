package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.security.JwtProvider;
import com.dynonuggets.refonteimplicaction.service.UserDetailsServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

public class ControllerIntegrationTestBase {
    static final String TOTAL_ELEMENTS_PATH = "$.totalElements";
    static final String TOTAL_PAGES_PATH = "$.totalPages";

    static final Pageable DEFAULT_PAGEABLE = PageRequest.of(0, 10);
    final Gson gson = new GsonBuilder().serializeNulls().create();

    @Autowired
    MockMvc mvc;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtProvider jwtProvider;
}
