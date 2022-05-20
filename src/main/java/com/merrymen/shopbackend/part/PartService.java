package com.merrymen.shopbackend.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    private final PartRepository partRepository;

    @Autowired
    public PartService(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public void addPart(Part part) {
        partRepository.saveAndFlush(part);
    }

    public Part findById(Long id) throws PartIDNotFoundException {
        Optional<Part> part = partRepository.findById(id);
        if (part.isPresent())
            return part.get();
        else
            throw new PartIDNotFoundException(id);
    }
}
