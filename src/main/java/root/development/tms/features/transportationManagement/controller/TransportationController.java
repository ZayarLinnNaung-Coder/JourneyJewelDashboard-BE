package root.development.tms.features.transportationManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.transportationManagement.repo.TransportationRepo;
import root.development.tms.global.document.Transportation;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transportations")
public class TransportationController {

    private final TransportationRepo transportationRepo;

    @PostMapping
    ResponseEntity<Transportation> saveNewTransportation(
            @RequestBody Transportation transportation
    ){
        return ResponseEntity.ok(transportationRepo.save(transportation));
    }

    @GetMapping("/{id}")
    ResponseEntity<Transportation> getPlaceDetails(
            @PathVariable String id
    ){
        return ResponseEntity.ok(transportationRepo.findById(id).get());
    }

    @GetMapping
    Page<Transportation> getAllTransportations(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (name == null || name.trim().isEmpty()) {
            return transportationRepo.findAll(pageable);
        } else {
            return transportationRepo.findByName(name, pageable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Transportation> editTransportation(
            @PathVariable String id,
            @RequestBody Transportation transportation
    ){
        transportation.setId(id);
        return ResponseEntity.ok(transportationRepo.save(transportation));
    }

}