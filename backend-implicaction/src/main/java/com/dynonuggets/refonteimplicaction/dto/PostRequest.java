package com.dynonuggets.refonteimplicaction.dto;

import lombok.*;

@Data
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String subredditName;
    private String name;
    private String url;
    private String description;
}
