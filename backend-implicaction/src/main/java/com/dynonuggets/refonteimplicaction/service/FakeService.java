package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.dto.FakeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FakeService {

    private AuthService authService;

    public FakeDto doTheFake() {
        return FakeDto.builder()
                .username(authService.getCurrentUser().getLogin())
                .build();
    }
}
