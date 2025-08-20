package root.development.tms.features.packageManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.global.document.Packages;
import root.development.tms.global.repo.PackageRepo;

@RestController
@AllArgsConstructor
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageRepo packageRepo;

    @PostMapping
    ResponseEntity<Packages> saveNewPackage(
            @RequestBody Packages transportation
    ){
        return ResponseEntity.ok(packageRepo.save(transportation));
    }

    @GetMapping("/{id}")
    ResponseEntity<Packages> getPlaceDetails(
            @PathVariable String id
    ){
        return ResponseEntity.ok(packageRepo.findById(id).get());
    }

    @GetMapping
    Page<Packages> getAllTransportations(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (name == null || name.trim().isEmpty()) {
            return packageRepo.findAll(pageable);
        } else {
            return packageRepo.findByName(name, pageable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Packages> editTransportation(
            @PathVariable String id,
            @RequestBody Packages transportation
    ){
        transportation.setId(id);
        return ResponseEntity.ok(packageRepo.save(transportation));
    }

}