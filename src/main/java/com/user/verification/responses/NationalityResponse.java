package com.user.verification.responses;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class NationalityResponse {
    private List<Country> country;

    @Data
    public static class Country {
    	@JsonProperty("country_id")
        private String countryId;
    }
}
