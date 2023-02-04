package com.dynonuggets.refonteimplicaction.dto;

import com.dynonuggets.refonteimplicaction.auth.rest.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;


@Data
@Builder
@Getter
@AllArgsConstructor
public class WorkExperienceDto {
    private Long id;
    /**
     * @deprecated depuis la v.2003 cet attribut ne doit plus être utilisé
     */
    @Deprecated(forRemoval = true, since = "v.2003")
    private UserDto user;
    private LocalDate startedAt;
    private LocalDate finishedAt;
    private String label;
    private String description;
    private String companyName;
}
