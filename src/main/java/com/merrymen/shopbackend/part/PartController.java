package com.merrymen.shopbackend.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "parts")
public class PartController {
    private final PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping
    public List<Part> getAllStudents() {
        return partService.getAllParts();
    }

    @PostMapping(path = "addPart")
    public void addPart(@Valid @RequestBody Part part) {
        Thread addPartThread = new Thread(() -> {
            PartUtilities.fetchCurrentPartPrice(part);
            partService.addPart(part);
        });
        addPartThread.start();
    }

    @GetMapping(path = "/{id}")
    public Part findById(@PathVariable("id") Long id) {
        try {
            return partService.findById(id);
        } catch (PartIDNotFoundException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, exc.getMessage(), exc);
        }
    }
}
