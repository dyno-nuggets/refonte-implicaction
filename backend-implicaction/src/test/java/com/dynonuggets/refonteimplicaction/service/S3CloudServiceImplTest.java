package com.dynonuggets.refonteimplicaction.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import io.findify.s3mock.S3Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import static com.dynonuggets.refonteimplicaction.service.S3CloudServiceImpl.BUCKET_NAME;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

class S3CloudServiceImplTest {

    public static final String S3_ENDPOINT = "http://localhost:8001";
    AmazonS3Client client;
    S3Mock api;

    CloudService cloudService;


    @BeforeEach
    void setUp() {
        api = new S3Mock.Builder().withPort(8001).withInMemoryBackend().build();
        api.start();
        EndpointConfiguration endpoint = new EndpointConfiguration(S3_ENDPOINT, "us-west-2");
        client = (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withPathStyleAccessEnabled(true)
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(new AnonymousAWSCredentials()))
                .build();

        client.createBucket(BUCKET_NAME);

        cloudService = new S3CloudServiceImpl(client);
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
                .ignoringFields("url")
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
