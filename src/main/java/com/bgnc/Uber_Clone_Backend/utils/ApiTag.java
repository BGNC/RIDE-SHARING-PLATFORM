package com.bgnc.Uber_Clone_Backend.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiTag {

    public static final String REGISTER = "/register";

    public static final String AUTHENTICATE = "/authenticate";

    public static final String DRIVER_ROUTE = "/driver/**";  // DRIVER-specific routes
    public static final String PASSENGER_ROUTE = "/passenger/**";

}
