package com.bgnc.Uber_Clone_Backend.service;

import com.bgnc.Uber_Clone_Backend.dto.AuthRequest;
import com.bgnc.Uber_Clone_Backend.dto.AuthResponse;




public interface IAuthenticationService {

        AuthRequest register(AuthRequest authRequest);
        AuthResponse authenticate(AuthRequest authRequest);

}


