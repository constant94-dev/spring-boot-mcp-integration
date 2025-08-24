package com.datapublic.mcp.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 공공데이터 포털 API 공통 응답 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicDataApiResponse<T> {
    
    @JsonProperty("response")
    private Response<T> response;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response<T> {
        @JsonProperty("header")
        private Header header;
        
        @JsonProperty("body")
        private Body<T> body;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {
        @JsonProperty("resultCode")
        private String resultCode;
        
        @JsonProperty("resultMsg")
        private String resultMsg;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body<T> {
        @JsonProperty("items")
        private Items<T> items;
        
        @JsonProperty("numOfRows")
        private int numOfRows;
        
        @JsonProperty("pageNo")
        private int pageNo;
        
        @JsonProperty("totalCount")
        private int totalCount;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Items<T> {
        @JsonProperty("item")
        private List<T> item;
    }
}

