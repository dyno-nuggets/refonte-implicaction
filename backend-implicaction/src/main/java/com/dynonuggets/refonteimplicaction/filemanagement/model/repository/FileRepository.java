package com.dynonuggets.refonteimplicaction.filemanagement.model.repository;

import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileModel, Long> {
    Optional<FileModel> findByObjectKeyAndPublicAccessIsTrue(String objectKey);
}
