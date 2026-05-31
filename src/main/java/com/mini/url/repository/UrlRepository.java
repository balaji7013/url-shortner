package com.mini.url.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mini.url.entity.UrlMapping;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping,Long> {

 Optional<UrlMapping> findByShortcode(String shortcode) ;
 Optional<UrlMapping> findByOriginalUrl(String originalUrl);
}
