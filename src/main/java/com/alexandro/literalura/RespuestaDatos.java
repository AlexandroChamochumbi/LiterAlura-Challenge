package com.alexandro.literalura;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaDatos {

    private Integer count;
    private List<LibroDatos> results;

    public Integer getCount() {
        return count;
    }

    public List<LibroDatos> getResults() {
        return results;
    }
}