package com.dynonuggets.refonteimplicaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReqisterRequestDto {
    private String username;
    private String email;
    private String password;
    private String nicename;
    private List<String> roles;
}
