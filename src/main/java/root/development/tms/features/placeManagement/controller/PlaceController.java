package root.development.tms.features.placeManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.placeManagement.repo.PlaceRepo;
import root.development.tms.global.document.Place;

@RestController
@AllArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceRepo placeRepo;

    @PostMapping
    ResponseEntity<Place> saveNewPlace(
            @RequestBody Place place
    ){
        return ResponseEntity.ok(placeRepo.save(place));
    }

    @GetMapping("/{id}")
    ResponseEntity<Place> getPlaceDetails(
            @PathVariable String id
    ){
        return ResponseEntity.ok(placeRepo.findById(id).get());
    }

    @GetMapping
    Page<Place> getAllPlaces(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (name == null || name.trim().isEmpty()) {
            return placeRepo.findAll(pageable);
        } else {
            return placeRepo.findByName(name, pageable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Place> editPlace(
            @PathVariable String id,
            @RequestBody Place place
    ){
        place.setId(id);
        return ResponseEntity.ok(placeRepo.save(place));
    }

}