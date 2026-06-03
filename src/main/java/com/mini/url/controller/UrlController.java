package com.mini.url.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mini.url.service.UrlService;

@RestController
@RequestMapping("/mini")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> handleRedirect(@PathVariable String shortcode) {
        String targetUrl = urlService.getOriginalUrl(shortcode);
        if ("Invalid shortcode!".equals(targetUrl)) {
            return ResponseEntity.badRequest().build();
        }
        urlService.incrementCount(shortcode);
        return ResponseEntity.status(HttpStatus.FOUND)
                             .location(URI.create(targetUrl))
                             .build();
    }

    @PostMapping
    public ResponseEntity<String> handleOriginalUrl(@RequestBody UrlRequest request) {
        String shortcode = urlService.addUrl(request.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(shortcode);
    }

    @GetMapping("/{shortcode}/analytics")
    public ResponseEntity<Long> analytics(@PathVariable String shortcode) {
        return ResponseEntity.ok(urlService.getAnalytics(shortcode));
    }

    public static class UrlRequest {
        private String originalUrl;

        public String getOriginalUrl() {
            return originalUrl;
        }

        public void setOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
        }
    }
}