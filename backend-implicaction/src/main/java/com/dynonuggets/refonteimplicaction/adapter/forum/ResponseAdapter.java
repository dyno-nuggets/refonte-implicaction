package com.dynonuggets.refonteimplicaction.adapter.forum;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.ResponseDto;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.model.forum.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ResponseAdapter {

    private final UserAdapter userAdapter;

    public Response toModel(ResponseDto dto, User user) {
        return Response.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .createdAt(dto.getCreatedAt())
                .editedAt(dto.getEditedAt())
                .author(user)
                .build();
    }

    public ResponseDto toDto(Response model) {

        return ResponseDto.builder()
                .id(model.getId())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .editedAt(model.getEditedAt())
                .author(userAdapter.toDto(model.getAuthor()))
                .build();
    }


}
