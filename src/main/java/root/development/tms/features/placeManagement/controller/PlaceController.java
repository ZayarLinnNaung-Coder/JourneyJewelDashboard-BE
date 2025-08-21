package root.development.tms.features.placeManagement.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.development.tms.features.placeManagement.repo.PlaceRepo;
import root.development.tms.global.document.Place;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceRepo placeRepository;
    private final MongoTemplate mongoTemplate;

    @PostMapping
    ResponseEntity<Place> saveNewPlace(
            @RequestBody Place place
    ){
        return ResponseEntity.ok(placeRepository.save(place));
    }

    @GetMapping("/suggest")
    ResponseEntity<List<String>> suggestPlace(
            @RequestParam("query") String query
    ){
        return ResponseEntity.ok(suggestPlaceNames(query));
    }

    public List<String> suggestPlaceNames(String query) {
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }

        // Fetch all places from database
        List<Place> allPlaces = placeRepository.findAll();
        String searchQuery = query.toLowerCase().trim();

        // Create a list to store places with their match scores
        List<PlaceMatch> matches = allPlaces.stream()
                .map(place -> new PlaceMatch(place, calculateMatchScore(place, searchQuery)))
                .filter(match -> match.score > 0) // Only include places with some match
                .sorted(Comparator.comparingInt((PlaceMatch m) -> m.score).reversed()) // Sort by best match first
                .collect(Collectors.toList());

        return matches.stream()
                .map(match -> match.place.getName())
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
    }

    private int calculateMatchScore(Place place, String query) {
        String name = place.getName() != null ? place.getName().toLowerCase() : "";
        String location = place.getPlace() != null ? place.getPlace().toLowerCase() : "";

        int maxScore = 0;

        // Check name field
        maxScore = Math.max(maxScore, getFieldMatchScore(name, query));

        // Check place field
        maxScore = Math.max(maxScore, getFieldMatchScore(location, query));

        return maxScore;
    }

    private int getFieldMatchScore(String field, String query) {
        if (field.isEmpty()) return 0;

        // Exact match gets highest score
        if (field.equals(query)) return 100;

        // Contains match gets high score
        if (field.contains(query)) return 90;

        // Check if field starts with query
        if (field.startsWith(query)) return 85;

        // Split both field and query into words for better matching
        String[] fieldWords = field.split("\\s+");
        String[] queryWords = query.split("\\s+");

        int bestWordScore = 0;

        // Check each combination of field words and query words
        for (String fieldWord : fieldWords) {
            for (String queryWord : queryWords) {
                int wordScore = getWordMatchScore(fieldWord, queryWord);
                bestWordScore = Math.max(bestWordScore, wordScore);
            }
        }

        // Also check the whole query against each field word
        for (String fieldWord : fieldWords) {
            int wordScore = getWordMatchScore(fieldWord, query);
            bestWordScore = Math.max(bestWordScore, wordScore);
        }

        return bestWordScore;
    }

    private int getWordMatchScore(String word, String query) {
        if (word.isEmpty() || query.isEmpty()) return 0;

        // Exact word match
        if (word.equals(query)) return 80;

        // Word contains query
        if (word.contains(query)) return 70;

        // Word starts with query
        if (word.startsWith(query)) return 65;

        // Query starts with word (for partial typing)
        if (query.startsWith(word)) return 60;

        // Fuzzy matching for typos
        if (query.length() >= 3) {
            int distance = calculateLevenshteinDistance(word, query);
            int maxLength = Math.max(word.length(), query.length());

            // Allow more errors for longer words
            int maxAllowedErrors = Math.max(1, maxLength / 4);

            if (distance <= maxAllowedErrors) {
                // Score based on similarity (fewer errors = higher score)
                return Math.max(10, 50 - (distance * 10));
            }
        }

        return 0;
    }

    private int calculateLevenshteinDistance(String a, String b) {
        if (a.length() == 0) return b.length();
        if (b.length() == 0) return a.length();

        int[][] matrix = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            matrix[i][0] = i;
        }

        for (int j = 0; j <= b.length(); j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                matrix[i][j] = Math.min(Math.min(
                                matrix[i - 1][j] + 1,      // deletion
                                matrix[i][j - 1] + 1),     // insertion
                        matrix[i - 1][j - 1] + cost // substitution
                );
            }
        }

        return matrix[a.length()][b.length()];
    }

    // Helper class to store place with match score
    private static class PlaceMatch {
        Place place;
        int score;

        PlaceMatch(Place place, int score) {
            this.place = place;
            this.score = score;
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<Place> getPlaceDetails(
            @PathVariable String id
    ){
        return ResponseEntity.ok(placeRepository.findById(id).get());
    }

    @GetMapping
    Page<Place> getAllPlaces(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "0") Integer page
    ){
        Pageable pageable = PageRequest.of(page, size);

        if (name == null || name.trim().isEmpty()) {
            return placeRepository.findAll(pageable);
        } else {
            return placeRepository.findByName(name, pageable);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Place> editPlace(
            @PathVariable String id,
            @RequestBody Place place
    ){
        place.setId(id);
        return ResponseEntity.ok(placeRepository.save(place));
    }
}