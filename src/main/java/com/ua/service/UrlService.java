package com.ua.service;

import com.ua.dto.UrlDTO;
import com.ua.dto.UrlStatDTO;
import com.ua.entity.UrlRecord;
import com.ua.repository.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public long saveUrl(UrlDTO urlDTO) {
        UrlRecord urlRecord = urlRepository.findByUrl(urlDTO.getUrl());
        if (urlRecord == null) {
            urlRecord = UrlRecord.of(urlDTO);
            urlRepository.save(urlRecord);
        }

        return urlRecord.getId();
    }

    @Transactional
    public String getUrl(long id) {
        var urlOpt = urlRepository.findById(id);
        if (urlOpt.isEmpty()) {
            return null;
        }

        UrlRecord urlRecord = urlOpt.get();
        urlRecord.setCount(urlRecord.getCount() + 1);
        urlRecord.setLastAccess(new Date());

        return urlRecord.getUrl();
    }

    @Transactional(readOnly = true)
    public List<UrlStatDTO> getStatistics() {
        var records = urlRepository.findAll();
        var result = new ArrayList<UrlStatDTO>();

        records.forEach((record) -> result.add(record.toStatDTO()));

        return result;
    }

    @Transactional(readOnly = true)
    public List<UrlRecord> getAll(){
        return urlRepository.findAll();
    }
    @Transactional
    public void delete(long id){
        urlRepository.deleteById(id);
    }
}
