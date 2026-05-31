package com.mini.url.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mini.url.service.UrlService;

@RestController
public class UrlController {

    private final UrlService urlService;
    public UrlController(UrlService urlService)
    {
        this.urlService =urlService;
    }
//get redirection via short url
//post new original url for creating short url or post old url and get old shortcode existed for it.
//get url analytic board of specific short code url

@GetMapping("/mini/{shortcode}")
public ResponseEntity<Void> handleRedirect(@PathVariable String shortcode)
{
    String targetUrl=urlService.getOriginalUrl(shortcode);
    if(("Invalid shortcode!").equals(targetUrl))
    {
        return ResponseEntity.badRequest().build();
    }
 return ResponseEntity.status(HttpStatus.FOUND)
                      .location(URI.create(targetUrl))
                      .build();
}
@PostMapping("/mini/{URL}")
public ResponseEntity<String> handleOrginalUrl(@PathVariable String URL)
{ 
    String shortcode=urlService.addUrl(URL);
    return   ResponseEntity.status(HttpStatus.CREATED).body(shortcode);
}
@GetMapping("mini/{shortcode}/analytics")
public ResponseEntity<Long> analytics(@PathVariable String shortcode)
{
    return ResponseEntity.status(HttpStatus.OK).body(urlService.getAnalytics(shortcode));
}
}