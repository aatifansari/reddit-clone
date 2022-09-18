package com.skywalker.reddit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "no_of_posts"
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class SubredditDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotBlank(message = "Subreddit name required")
    private String subredditName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("no_of_posts")
    private Integer numberOfPosts;
}
