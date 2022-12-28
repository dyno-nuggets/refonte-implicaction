package com.dynonuggets.refonteimplicaction.service.forum;

import com.dynonuggets.refonteimplicaction.adapter.forum.ResponseAdapter;
import com.dynonuggets.refonteimplicaction.dto.forum.ResponseDto;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.model.forum.Response;
import com.dynonuggets.refonteimplicaction.model.forum.Topic;
import com.dynonuggets.refonteimplicaction.repository.forum.ResponseRepository;
import com.dynonuggets.refonteimplicaction.repository.forum.TopicRepository;
import com.dynonuggets.refonteimplicaction.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dynonuggets.refonteimplicaction.utils.Message.RESPONSE_NOT_FOUND_MESSAGE;

@AllArgsConstructor
@Service
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final ResponseAdapter responseAdapter;
    private final AuthService authService;

    public Page<ResponseDto> getResponsesFromTopic(long topicId, Pageable pageable) {
        Topic topic = topicRepository.getById(topicId);
        return responseRepository.findAllByTopic(topic, pageable).map(responseAdapter::toDto);
    }

    public ResponseDto createResponse(ResponseDto responseDto) {
        Response response = responseAdapter.toModel(responseDto, authService.getCurrentUser());
        Response save = responseRepository.save(response);
        return responseAdapter.toDto(save);
    }

    @Transactional
    public void deleteResponse(Long responseId) {
        Response response = findById(responseId);
        responseRepository.delete(response);
    }
    
    private Response findById(Long responseId) {
        return responseRepository.findById(responseId)
                .orElseThrow(() -> new NotFoundException(String.format(RESPONSE_NOT_FOUND_MESSAGE, responseId)));
    }
}
