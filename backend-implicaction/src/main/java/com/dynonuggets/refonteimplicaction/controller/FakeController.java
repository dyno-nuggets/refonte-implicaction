package com.dynonuggets.refonteimplicaction.controller;

import com.dynonuggets.refonteimplicaction.dto.FakeDto;
import com.dynonuggets.refonteimplicaction.service.FakeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fake")
@AllArgsConstructor
public class FakeController {

    private final FakeService fakeService;

    @GetMapping
    public ResponseEntity<FakeDto> getFake() {
        return new ResponseEntity<>(fakeService.doTheFake(), HttpStatus.OK);
    }
}
