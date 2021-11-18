package com.dynonuggets.refonteimplicaction.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class S3CloudServiceImplTest {

    static final String S3_ENDPOINT = "http://localhost:8001";
    static final String BUCKET_NAME = "refonte-implicaction";
    static final String SIGNING_REGION = "us-west-2";

    AmazonS3Client client;
    S3Mock api;
    CloudService cloudService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        EndpointConfiguration endpoint = new EndpointConfiguration(S3_ENDPOINT, SIGNING_REGION);
        client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        client.createBucket("refonte-implicaction");

        cloudService = new S3CloudServiceImpl(client, fileRepository);
    }

    @Test
    void shoud_create_client() throws IOException {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.txt",
                "text/plain",
                "test data".getBytes()
        );
        FileModel expectedFile = FileModel.builder()
                .filename("test.txt")
                .contentType("text/plain")
                .build();

        // when
        final FileModel actualFile = cloudService.uploadFile(mockMultipartFile);

        // then

        assertThat(actualFile).usingRecursiveComparison()
                .ignoringFields("url", "objectKey")
                .isEqualTo(expectedFile);

        assertThat(actualFile.getUrl()).startsWith(S3_ENDPOINT + "/" + BUCKET_NAME);

        // vérification de l'existence du fichier uploadé
        HttpURLConnection connection = (HttpURLConnection) new URL(actualFile.getUrl()).openConnection();
        assertThat(connection.getResponseCode()).isEqualTo(HTTP_OK);
    }

    @AfterEach
    void tearDown() {
        api.shutdown();
    }
}
