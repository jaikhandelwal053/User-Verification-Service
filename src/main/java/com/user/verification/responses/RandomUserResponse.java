package com.user.verification.responses;

import java.util.List;

import lombok.Data;


@Data
public class RandomUserResponse {
    private List<UserData> results;

    @Data
    public static class UserData {
        private Name name;
        private String gender;
        private String nat;
        private Dob dob;

        @Data
        public static class Name {
            private String first;
            private String last;
        }

        @Data
        public static class Dob {
            private String date;
            private int age;
        }
    }
}
//@Data
//public class RandomUserResponse {
//    private List<UserData> results;
//
//    @Data
//    public static class UserData {
//        private Name name;
//        private String gender;
//        private Dob dob;
//        private String nat;
//
//        @Data
//        public static class Name {
//            private String first;
//            private String last;
//        }
//
//        @Data
//        public static class Dob {
//            private int age;
//            private String date;
//        }
//    }
//}
