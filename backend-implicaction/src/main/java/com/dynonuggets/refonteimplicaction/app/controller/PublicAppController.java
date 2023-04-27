package com.dynonuggets.refonteimplicaction.app.controller;

import com.dynonuggets.refonteimplicaction.app.dto.App;
import com.dynonuggets.refonteimplicaction.app.service.AppService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.dynonuggets.refonteimplicaction.app.utils.AppUris.APP_BASE_URI;

@RestController
@AllArgsConstructor
@RequestMapping(APP_BASE_URI)
public class PublicAppController {

    private final AppService appService;

    @GetMapping
    public ResponseEntity<App> getApp() {
        return ResponseEntity.ok(appService.getApp());
    }
}
