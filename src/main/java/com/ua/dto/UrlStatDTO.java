package com.ua.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UrlStatDTO extends UrlResultDTO {
    private long redirects;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd (HH:mm:ss)", timezone = "Europe/Kiev")
    private Date lastAccess;

}
