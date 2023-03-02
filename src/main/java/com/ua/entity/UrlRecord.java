package com.ua.entity;

import com.ua.dto.UrlDTO;
import com.ua.dto.UrlStatDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity
@Data
public class UrlRecord {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Long count;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastAccess;

    public UrlRecord() {
        count = 0L;
        lastAccess = new Date();
    }

    public UrlRecord(String url) {
        this();
        this.url = url;
    }
    public static UrlRecord of(UrlDTO urlDTO){
        return new UrlRecord(urlDTO.getUrl());
    }
    public UrlStatDTO toStatDTO (){
        UrlStatDTO result = new UrlStatDTO();

        result.setUrl(url);
        result.setShortUrl(Long.toString(id));
        result.setRedirects(count);
        result.setLastAccess(lastAccess);

        return result;
    }
}
