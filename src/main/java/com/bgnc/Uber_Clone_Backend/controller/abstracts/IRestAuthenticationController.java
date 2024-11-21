package com.bgnc.Uber_Clone_Backend.controller.abstracts;

import com.bgnc.Uber_Clone_Backend.controller.RestBaseController;
import com.bgnc.Uber_Clone_Backend.controller.RootEntity;
import com.bgnc.Uber_Clone_Backend.dto.AuthRequest;
import com.bgnc.Uber_Clone_Backend.dto.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bgnc.Uber_Clone_Backend.utils.ApiTag.REGISTER;

public interface IRestAuthenticationController  {


    RootEntity<AuthRequest> register(@Valid @RequestBody AuthRequest authRequest);
    RootEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest authRequest);
}
