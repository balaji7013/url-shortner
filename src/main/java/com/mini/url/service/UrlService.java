package com.mini.url.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mini.url.entity.UrlMapping;
import com.mini.url.repository.UrlRepository;

@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final Base62 base62;

    public UrlService(UrlRepository urlRepository, Base62 base62) {
        this.urlRepository = urlRepository;
        this.base62 = base62;
    }

    public String addUrl(String url) {
        Optional<UrlMapping> urlupdate = urlRepository.findByOriginalUrl(url);
        if (urlupdate.isPresent()) {
            return urlupdate.get().getShortcode();
        }
        UrlMapping urlconfig = new UrlMapping();
        urlconfig.setOriginalUrl(url);
        urlconfig = urlRepository.save(urlconfig);
        String shortcode = base62.encode(urlconfig.getId());
        urlconfig.setShortcode(shortcode);
        urlRepository.save(urlconfig);
        return shortcode;
    }

    @Cacheable(value = "url", key = "#shortcode", unless = "#result == null")
    public String getOriginalUrl(String shortcode) {
        Optional<UrlMapping> urlcontainer = urlRepository.findByShortcode(shortcode);
        return urlcontainer.map(UrlMapping::getOriginalUrl).orElse(null);
    }

    @CacheEvict(value = "url", key = "#shortcode")
    public void incrementCount(String shortcode) {
        Optional<UrlMapping> urlcontainer = urlRepository.findByShortcode(shortcode);
        if (urlcontainer.isPresent()) {
            UrlMapping mapping = urlcontainer.get();
            mapping.setCount(mapping.getCount() + 1);
            urlRepository.save(mapping);
        }
    }

    public Long getAnalytics(String shortcode) {
        return urlRepository.findByShortcode(shortcode).map(UrlMapping::getCount).orElse(0L);
    }
}
