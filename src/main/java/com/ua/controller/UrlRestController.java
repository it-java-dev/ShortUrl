package com.ua.controller;

import com.ua.dto.UrlDTO;
import com.ua.dto.UrlResultDTO;
import com.ua.dto.UrlStatDTO;
import com.ua.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class UrlRestController {

    private final UrlService urlService;

    public UrlRestController(UrlService urlService) {
        this.urlService = urlService;
    }
    @PostMapping ("shorten/json")
    public UrlResultDTO shorten (@RequestBody UrlDTO urlDTO){
        long id = urlService.saveUrl(urlDTO);

        UrlResultDTO result = new UrlResultDTO();
        result.setUrl(urlDTO.getUrl());
        result.setShortUrl(Long.toString(id));

        return result;
    }

    @GetMapping("my/{id}")
    public ResponseEntity<Void> redirect (@PathVariable("id") Long id){
        String url = urlService.getUrl(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        headers.setCacheControl("no-cache, no-store, must-revalidate");

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @GetMapping ("stat")
    public List<UrlStatDTO> stat(){
        return urlService.getStatistics();
    }
}
