package site.kpokogujl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pet {

    private long id;
    private String name;
    @JsonProperty("status")
    String status;
    @JsonProperty("photoUrls")
    private List<String> photoUrls;
    @JsonProperty("tags")
    List<Tags> tags;
    @JsonProperty("category")
    Category category;

}
