package g26.eDucaApp.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/progress")
@RestControllerAdvice
public class ProgressController {

    // made in the api but only used internally between the data_gen scripts and the api

    private Map<String, AtomicInteger> insertionProgressMap = new ConcurrentHashMap<>();
    private Map<String, Integer> totalExpectedMap = new ConcurrentHashMap<>();


    @Operation(summary = "Get the progress of the insertion of a certain type of data (students, teachers, subjects, classes, grades) mainly used internally between the data_gen script and the api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the progress of the insertion of a certain type of data"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @GetMapping("/insertion/{type}")
    public double getInsertionProgress(@PathVariable String type) {
        insertionProgressMap.putIfAbsent(type, new AtomicInteger(0)); // Initialize if not present
        int progress = insertionProgressMap.get(type).get();
        
        totalExpectedMap.putIfAbsent(type, 0); // Initialize if not present
        int totalExpected = totalExpectedMap.get(type);
        
        if (totalExpected == 0) {
            return 0; // To avoid division by zero
        }
        
        return (double) progress / totalExpected * 100;
    }

    public void incrementProgress(String type) {
        insertionProgressMap.computeIfAbsent(type, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getTotalExpected(String type) {
        return totalExpectedMap.getOrDefault(type, 0);
    }

    public void setTotalExpectedCount(String type, int totalExpected) {
        insertionProgressMap.put(type, new AtomicInteger(0)); // Reset the progress for this type
        totalExpectedMap.put(type, totalExpected); // Set the total expected count for this type
    }
}

