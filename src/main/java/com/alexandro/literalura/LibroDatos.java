package com.alexandro.literalura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDatos {

    private String title;
    private List<AutorDatos> authors;
    private List<String> languages;

    @JsonProperty("download_count")
    private Integer downloadCount;

    public String getTitle() {
        return title;
    }

    public List<AutorDatos> getAuthors() {
        return authors;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }
}