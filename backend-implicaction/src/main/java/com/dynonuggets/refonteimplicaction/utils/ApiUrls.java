package com.dynonuggets.refonteimplicaction.utils;

public class ApiUrls {

    private ApiUrls() {
        // empêche la construction d'un objet ApiUrls
    }

    public static class Job {
        public static final String BASE_URI = "/api/job-postings";
        public static final String GET_JOB_URI = "/{jobId}";
        public static final String DELETE_JOB_URI = "/{jobId}";

        private Job() {
            // empêche la construction d'un objet
        }
    }

    public static class Subreddit {

        public static final String BASE_URI = "/api/sub-reddit";

        private Subreddit() {
            // empêche la construction d'un objet
        }
    }
}
