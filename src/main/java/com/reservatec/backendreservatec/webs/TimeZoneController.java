package com.reservatec.backendreservatec.webs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;

@RestController
public class TimeZoneController {

    @GetMapping("/current-time")
    public String getCurrentTime() {
        return "Current time in Peru: " + ZonedDateTime.now();
    }
}
