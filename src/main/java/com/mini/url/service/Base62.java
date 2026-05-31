package com.mini.url.service;

import org.springframework.stereotype.Service;

@Service
class Base62
{
    public static final String alphabet="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String encode(long id)
    {
        if(id==0){
            return "0";
        }
        
        StringBuilder sb= new StringBuilder();
        while(id>0)
        {
            sb.append(alphabet.charAt((int)(id%62)));
            id=id/62;
        }
        return sb.reverse().toString();
    }
    public long decode(String shortcode)
    {
        long num=0;
        for(int i=0;i<shortcode.length();i++)
        {
            num=(num*62)+alphabet.indexOf(shortcode.charAt(i));
        }
        return num;
    }
}