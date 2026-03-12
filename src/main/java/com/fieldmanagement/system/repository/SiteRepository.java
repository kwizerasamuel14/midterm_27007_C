package com.fieldmanagement.system.repository;

import com.fieldmanagement.system.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    boolean existsByName(String name);
}
