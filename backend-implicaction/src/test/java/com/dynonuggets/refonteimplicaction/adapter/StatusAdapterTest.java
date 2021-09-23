package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.StatusDto;
import com.dynonuggets.refonteimplicaction.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusAdapterTest {
    Status status;
    StatusDto statusDto;
    StatusAdapter statusAdapter;

    @BeforeEach
    public void SetUp() {
        statusAdapter = new StatusAdapter();

        status = Status.builder()
                .id(1L)
                .label("labelModel")
                .type("typeModel")
                .build();

        statusDto = StatusDto.builder()
                .id(2L)
                .label("labelDto")
                .type("typeDto")
                .build();
    }

    @Test
    void toDtoTest() {
        StatusDto statusDtoTest = statusAdapter.toDto(status);

        assertThat(statusDtoTest.getId()).isEqualTo(status.getId());
        assertThat(statusDtoTest.getLabel()).isEqualTo(status.getLabel());
        assertThat(statusDtoTest.getType()).isEqualTo(status.getType());
    }

    @Test
    void toModelTest() {
        Status statusTest = statusAdapter.toModel(statusDto);

        assertThat(statusTest.getId()).isEqualTo(statusDto.getId());
        assertThat(statusTest.getLabel()).isEqualTo(statusDto.getLabel());
        assertThat(statusTest.getType()).isEqualTo(statusDto.getType());
    }
}
