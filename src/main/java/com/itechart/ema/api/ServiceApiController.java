package com.itechart.ema.api;

import com.itechart.generated.api.ServiceApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class ServiceApiController implements ServiceApi {

    public static final String HEALTH_CHECK_PATH = "/api/v1/health";

    @Override
    public ResponseEntity<Void> healthCheck() {
        return new ResponseEntity<>(OK);
    }

}
