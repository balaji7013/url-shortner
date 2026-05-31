package com.mini.url.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mini.url.entity.UrlMapping;
import com.mini.url.repository.UrlRepository;

@Service
public class UrlService {
    private  final UrlRepository urlRepository;
    private final Base62 base62;
 

    public UrlService(UrlRepository urlRepository,Base62 base62)
    {
        this.urlRepository=urlRepository;
        this.base62=base62;
    }
    public String addUrl(String url)
    {
        Optional<UrlMapping> urlupdate =urlRepository.findByOriginalUrl(url);
        if(urlupdate.isPresent())
        {
            return urlupdate.get().getShortcode();
        }
        else
        {
        UrlMapping urlconfig=new UrlMapping();
        urlconfig.setOriginalUrl(url);
        urlconfig=urlRepository.save(urlconfig);
        String shortcode=base62.encode(urlconfig.getId());
        urlconfig.setShortcode(shortcode);

        urlRepository.save(urlconfig);
        return shortcode;
        }
        
    }
    public String getOriginalUrl(String shortcode)
    {
        Optional<UrlMapping> urlcontainer=urlRepository.findByShortcode(shortcode);
        if(urlcontainer.isPresent())
        {
            urlcontainer.get().setCount(urlcontainer.get().getCount()+1);
            return urlcontainer.get().getOriginalUrl();
        }
        else
        {
            return "Invalid shortcode!";
        }

    }
    public Long getAnalytics(String shortcode)
    {
        Optional<UrlMapping> urlcontainer=urlRepository.findByShortcode(shortcode);
        return urlcontainer.get().getCount();
    }

    
}
