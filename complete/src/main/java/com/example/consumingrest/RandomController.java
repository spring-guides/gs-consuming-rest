package com.example.consumingrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api")
public class RandomController {

    @GetMapping("/random")
    public RandomQuoteResponse random() {
        long id = ThreadLocalRandom.current().nextLong(1, 10_000);
        List<String> messages = List.of(
                "All limitations are self-imposed.",
                "When nothing goes right, go left.",
                "Dreams don’t work unless you do.",
                "Simplicity is the ultimate sophistication.",
                "Stay hungry, stay foolish."
        );
        String message = messages.get(ThreadLocalRandom.current().nextInt(messages.size()));
        return new RandomQuoteResponse("success", new RandomValueResponse(id, message));
    }

    public static final class RandomValueResponse {
        private final long id;
        private final String quote;

        public RandomValueResponse(long id, String quote) {
            this.id = id;
            this.quote = quote;
        }

        public long getId() {
            return id;
        }

        public String getQuote() {
            return quote;
        }
    }

    public static final class RandomQuoteResponse {
        private final String type;
        private final RandomValueResponse value;

        public RandomQuoteResponse(String type, RandomValueResponse value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public RandomValueResponse getValue() {
            return value;
        }
    }
}
