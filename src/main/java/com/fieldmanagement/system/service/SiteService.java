package com.fieldmanagement.system.service;

import com.fieldmanagement.system.entity.Site;
import com.fieldmanagement.system.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SiteService {
    
    @Autowired
    private SiteRepository siteRepository;
    
    public Site saveSite(Site site) {
        return siteRepository.save(site);
    }
    
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }
}
