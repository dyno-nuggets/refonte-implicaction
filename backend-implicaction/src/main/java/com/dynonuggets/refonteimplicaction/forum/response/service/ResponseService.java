package com.dynonuggets.refonteimplicaction.forum.response.service;

import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.forum.response.adapter.ResponseAdapter;
import com.dynonuggets.refonteimplicaction.forum.response.domain.model.ResponseModel;
import com.dynonuggets.refonteimplicaction.forum.response.domain.repository.ResponseRepository;
import com.dynonuggets.refonteimplicaction.forum.response.dto.ResponseDto;
import com.dynonuggets.refonteimplicaction.forum.topic.domain.model.TopicModel;
import com.dynonuggets.refonteimplicaction.forum.topic.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.forum.response.error.ResponseErrorResult.RESPONSE_NOT_FOUND;
import static java.lang.String.valueOf;


@AllArgsConstructor
@Service
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final TopicService topicService;
    private final ResponseAdapter responseAdapter;
    private final ProfileService profileService;

    @Transactional(readOnly = true)
    public Page<ResponseDto> getResponsesFromTopic(final long topicId, final Pageable pageable) {
        final TopicModel topic = topicService.getByIdIfExists(topicId);
        return responseRepository.findAllByTopic(topic, pageable).map(responseAdapter::toDto);
    }

    @Transactional
    public ResponseDto createResponse(final ResponseDto responseDto) {
        final ResponseModel response = responseAdapter.toModel(responseDto);
        response.setAuthor(profileService.getCurrentProfile());
        final ResponseModel save = responseRepository.save(response);
        return responseAdapter.toDto(save);
    }

    @Transactional
    public void deleteResponse(final Long responseId) {
        final ResponseModel response = getByIdIfExists(responseId);
        responseRepository.delete(response);
    }

    private ResponseModel getByIdIfExists(final Long responseId) {
        return responseRepository.findById(responseId)
                .orElseThrow(() -> new EntityNotFoundException(RESPONSE_NOT_FOUND, valueOf(responseId)));
    }
}
