package com.dynonuggets.refonteimplicaction.forum.response.adapter;

import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.response.dto.ResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ResponseAdapter {

    private final ProfileAdapter profileAdapter;

    public ResponseModel toModel(final ResponseDto dto) {
        return ResponseModel.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .createdAt(dto.getCreatedAt())
                .editedAt(dto.getEditedAt())
                .build();
    }

    public ResponseDto toDto(final ResponseModel model) {

        return ResponseDto.builder()
                .id(model.getId())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .editedAt(model.getEditedAt())
                .author(profileAdapter.toDto(model.getAuthor()))
                .build();
    }


}
