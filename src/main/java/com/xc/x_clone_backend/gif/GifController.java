package com.xc.x_clone_backend.gif;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/gifs")
public class GifController {
    @Value("${GIPHY_API_KEY}")
    private String giphyApiKey;
    
    private final WebClient webClient;
    
    @Autowired
    public GifController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.giphy.com/v1/gifs").build();
    }
    
    @GetMapping
    public Mono<GiphyResponse> getTrending() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        System.out.println("Authenticated user: " + username);

        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/trending")
                .queryParam("api_key", giphyApiKey)
                .queryParam("limit", 20)
                .queryParam("rating", "pg-13")
                .build())
            .retrieve()
            .bodyToMono(GiphyResponse.class);
    }
    
    @GetMapping("/search")
    public Mono<GiphyResponse> searchGifs(@RequestParam String query) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/search")
                .queryParam("api_key", giphyApiKey)
                .queryParam("q", query)
                .queryParam("limit", 20)
                .queryParam("rating", "pg-13")
                .build())
            .retrieve()
            .bodyToMono(GiphyResponse.class);
    }
    @GetMapping("/test")
public ResponseEntity<String> test() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return new ResponseEntity<>("Authenticated as: " + auth.getName(), HttpStatus.OK);
}
}







