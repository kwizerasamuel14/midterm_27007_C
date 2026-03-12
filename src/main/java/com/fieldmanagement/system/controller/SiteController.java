package com.fieldmanagement.system.controller;

import com.fieldmanagement.system.entity.Site;
import com.fieldmanagement.system.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sites")
public class SiteController {
    
    @Autowired
    private SiteService siteService;
    
    @PostMapping
    public ResponseEntity<Site> createSite(@RequestBody Site site) {
        return new ResponseEntity<>(siteService.saveSite(site), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Site>> getAllSites() {
        return ResponseEntity.ok(siteService.getAllSites());
    }
}
