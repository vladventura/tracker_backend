package com.merrymen.shopbackend.part;

public class PartIDNotFoundException extends Exception {
    public PartIDNotFoundException(Long id) {
        super("Nothing found for ID #" + id);
    }
}
