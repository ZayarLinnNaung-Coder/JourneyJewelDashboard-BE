package root.development.tms.features.hotelManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.hotelManagement.repo.HotelRepo;
import root.development.tms.global.document.Hotels;

@RestController
@AllArgsConstructor
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelRepo hotelRepo;

    @PostMapping
    ResponseEntity<Hotels> saveNewTransportation(
            @RequestBody Hotels transportation
    ){
        return ResponseEntity.ok(hotelRepo.save(transportation));
    }

    @GetMapping("/{id}")
    ResponseEntity<Hotels> getPlaceDetails(
            @PathVariable String id
    ){
        return ResponseEntity.ok(hotelRepo.findById(id).get());
    }

    @GetMapping
    Page<Hotels> getAllTransportations(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (name == null || name.trim().isEmpty()) {
            return hotelRepo.findAll(pageable);
        } else {
            return hotelRepo.findByName(name, pageable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Hotels> editTransportation(
            @PathVariable String id,
            @RequestBody Hotels transportation
    ){
        transportation.setId(id);
        return ResponseEntity.ok(hotelRepo.save(transportation));
    }

}