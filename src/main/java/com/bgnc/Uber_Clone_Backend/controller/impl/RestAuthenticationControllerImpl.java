package com.bgnc.Uber_Clone_Backend.controller.impl;

import com.bgnc.Uber_Clone_Backend.controller.RestBaseController;
import com.bgnc.Uber_Clone_Backend.controller.RootEntity;
import com.bgnc.Uber_Clone_Backend.controller.abstracts.IRestAuthenticationController;
import com.bgnc.Uber_Clone_Backend.dto.AuthRequest;
import com.bgnc.Uber_Clone_Backend.dto.AuthResponse;
import com.bgnc.Uber_Clone_Backend.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bgnc.Uber_Clone_Backend.utils.ApiTag.AUTHENTICATE;
import static com.bgnc.Uber_Clone_Backend.utils.ApiTag.REGISTER;


@RestController
@RequiredArgsConstructor
public class RestAuthenticationControllerImpl extends RestBaseController implements IRestAuthenticationController {

    private final IAuthenticationService authService;

    @Override
    @PostMapping(REGISTER)
    public RootEntity<AuthRequest> register(@Valid @RequestBody AuthRequest authRequest) {
        return ok(authService.register(authRequest));
    }

    @Override
    @PostMapping(AUTHENTICATE)
    public RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest) {
        return ok(authService.authenticate(authRequest));

    }
}
