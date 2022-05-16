package com.merrymen.shopbackend.part;

import org.springframework.validation.BindException;
import org.springframework.web.client.RestClientException;

public class PartIDNotFoundException extends Exception {
    public PartIDNotFoundException(Long id){
        super("Nothing found for ID #" + id);
    }
}
