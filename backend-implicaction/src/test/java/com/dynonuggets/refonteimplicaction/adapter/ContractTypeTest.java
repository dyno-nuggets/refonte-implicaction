package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.ContractTypeDto;
import com.dynonuggets.refonteimplicaction.model.ContractType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContractTypeTest {

    ContractType contractType;
    ContractTypeDto contractTypeDto;
    ContractTypeAdapter contractTypeAdapter;

    @BeforeEach
    public void SetUp() {
        contractTypeAdapter = new ContractTypeAdapter();

        contractType = ContractType.builder()
                .id(1L)
                .label("labelModel")
                .build();

        contractTypeDto = ContractTypeDto.builder()
                .id(2L)
                .label("labelDto")
                .build();
    }

    @Test
    void toDtoTest() {
        ContractTypeDto contractTypeDtoTest = contractTypeAdapter.toDto(contractType);

        assertThat(contractTypeDtoTest.getId()).isEqualTo(contractType.getId());
        assertThat(contractTypeDtoTest.getLabel()).isEqualTo(contractType.getLabel());
    }

    @Test
    void toModelTest() {
        ContractType contractTypeTest = contractTypeAdapter.toModel(contractTypeDto);

        assertThat(contractTypeTest.getId()).isEqualTo(contractTypeDto.getId());
        assertThat(contractTypeTest.getLabel()).isEqualTo(contractTypeDto.getLabel());
    }
}
