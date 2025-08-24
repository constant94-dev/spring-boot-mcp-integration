package com.datapublic.mcp.web.service;

import com.datapublic.mcp.web.dto.ApartmentRentItem;
import com.datapublic.mcp.web.dto.PublicDataApiResponse;
import com.datapublic.mcp.web.exception.PublicDataApiException;
import com.datapublic.mcp.web.exception.PublicDataErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * 공공데이터 포털 API 클라이언트
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PublicDataApiClient {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    @Value("${public.data.service.key}")
    private String serviceKey;
    
    @Value("${public.data.base.url:https://apis.data.go.kr}")
    private String baseUrl;
    
    @Value("${public.data.timeout:30}")
    private int timeout;
    
    @Value("${public.data.retry.count:3}")
    private int retryCount;
    
    /**
     * 공공데이터 포털 API 호출
     * 
     * @param endpoint API 엔드포인트
     * @param params 요청 파라미터
     * @param responseType 응답 타입
     * @return API 응답
     */
    public <T> PublicDataApiResponse<T> callApi(String endpoint, Map<String, String> params, Class<T> responseType) {
        log.info("🌐 공공데이터 API 호출 - 엔드포인트: {}, 파라미터: {}", endpoint, params);
        
        try {
            // 기본 파라미터 추가
            params.put("serviceKey", serviceKey);
            
            String response = webClient.get()
                    .uri(baseUrl + endpoint, uriBuilder -> {
                        params.forEach(uriBuilder::queryParam);
                        return uriBuilder.build();
                    })
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(timeout))
                    .retry(retryCount)
                    .block();
            
            log.debug("📡 API 응답: {}", response);
            
            // 응답 파싱 및 검증
            return parseAndValidateResponse(response, responseType);
            
        } catch (WebClientResponseException e) {
            log.error("❌ API 호출 실패 - HTTP 상태: {}, 응답: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new PublicDataApiException("HTTP_ERROR", "API 호출 중 HTTP 오류 발생: " + e.getStatusCode());
            
        } catch (Exception e) {
            log.error("❌ API 호출 중 예외 발생", e);
            throw new PublicDataApiException("UNKNOWN_ERROR", "알 수 없는 오류: " + e.getMessage(), e);
        }
    }
    
    /**
     * 응답 파싱 및 검증
     */
    private <T> PublicDataApiResponse<T> parseAndValidateResponse(String response, Class<T> responseType) {
        try {
            // JSON 파싱 (실제 구현에서는 Jackson ObjectMapper 사용)
            PublicDataApiResponse<T> apiResponse = parseJsonResponse(response, responseType);
            
            // 응답 헤더 검증
            validateResponseHeader(apiResponse.getResponse().getHeader());
            
            return apiResponse;
            
        } catch (Exception e) {
            log.error("❌ 응답 파싱 실패", e);
            throw new PublicDataApiException("PARSE_ERROR", "응답 파싱 중 오류 발생: " + e.getMessage(), e);
        }
    }
    
    /**
     * JSON 응답 파싱 (Jackson ObjectMapper 사용)
     * XML 응답도 처리할 수 있도록 수정
     */
    private <T> PublicDataApiResponse<T> parseJsonResponse(String response, Class<T> responseType) {
        try {
            log.debug("🔍 응답 파싱 시작: {}", response.substring(0, Math.min(200, response.length())) + "...");
            
            // XML 응답인지 확인 (OpenAPI_ServiceResponse, cmmMsgHeader 등 포함)
            if (response.trim().startsWith("<?xml") || 
                response.trim().startsWith("<response") ||
                response.contains("<OpenAPI_ServiceResponse>") ||
                response.contains("<cmmMsgHeader>")) {
                log.debug("📄 XML 응답 감지됨");
                return parseXmlResponse(response, responseType);
            }
            
            // JSON 응답 파싱
            JsonNode rootNode = objectMapper.readTree(response);
            
            // response 노드 확인
            if (!rootNode.has("response")) {
                throw new PublicDataApiException("INVALID_RESPONSE_FORMAT", "응답에 'response' 노드가 없습니다.");
            }
            
            JsonNode responseNode = rootNode.get("response");
            
            // PublicDataApiResponse 객체 생성
            PublicDataApiResponse<T> apiResponse = new PublicDataApiResponse<>();
            PublicDataApiResponse.Response<T> responseObj = new PublicDataApiResponse.Response<>();
            apiResponse.setResponse(responseObj);
            
            // Header 파싱
            if (responseNode.has("header")) {
                JsonNode headerNode = responseNode.get("header");
                PublicDataApiResponse.Header header = objectMapper.treeToValue(headerNode, PublicDataApiResponse.Header.class);
                responseObj.setHeader(header);
            }
            
            // Body 파싱
            if (responseNode.has("body")) {
                JsonNode bodyNode = responseNode.get("body");
                PublicDataApiResponse.Body<T> body = new PublicDataApiResponse.Body<>();
                responseObj.setBody(body);
                
                // 페이지 정보 파싱
                if (bodyNode.has("pageNo")) {
                    body.setPageNo(bodyNode.get("pageNo").asInt());
                }
                if (bodyNode.has("numOfRows")) {
                    body.setNumOfRows(bodyNode.get("numOfRows").asInt());
                }
                if (bodyNode.has("totalCount")) {
                    body.setTotalCount(bodyNode.get("totalCount").asInt());
                }
                
                // Items 파싱
                if (bodyNode.has("items")) {
                    JsonNode itemsNode = bodyNode.get("items");
                    PublicDataApiResponse.Items<T> items = new PublicDataApiResponse.Items<>();
                    body.setItems(items);
                    
                    // item 배열 파싱
                    if (itemsNode.has("item")) {
                        JsonNode itemArray = itemsNode.get("item");
                        
                        if (itemArray.isArray()) {
                            // 배열인 경우
                            List<T> itemList = objectMapper.convertValue(itemArray, 
                                    objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
                            items.setItem(itemList);
                        } else {
                            // 단일 객체인 경우
                            T singleItem = objectMapper.treeToValue(itemArray, responseType);
                            items.setItem(List.of(singleItem));
                        }
                    }
                }
            }
            
            log.debug("✅ JSON 응답 파싱 완료");
            return apiResponse;
            
        } catch (JsonProcessingException e) {
            log.error("❌ JSON 파싱 오류", e);
            throw new PublicDataApiException("JSON_PARSE_ERROR", "JSON 파싱 중 오류 발생: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("❌ 응답 파싱 중 예외 발생", e);
            throw new PublicDataApiException("PARSE_ERROR", "응답 파싱 중 오류 발생: " + e.getMessage(), e);
        }
    }
    
    /**
     * XML 응답 파싱 (공공데이터 포털 실제 응답 형식에 맞게 수정)
     * 실제 응답: <OpenAPI_ServiceResponse><cmmMsgHeader><errMsg>SERVICE ERROR</errMsg>...
     */
    private <T> PublicDataApiResponse<T> parseXmlResponse(String response, Class<T> responseType) {
        try {
            log.debug("🔍 공공데이터 포털 XML 응답 파싱 시작");
            log.debug("📄 원본 응답: {}", response);
            
            PublicDataApiResponse<T> apiResponse = new PublicDataApiResponse<>();
            PublicDataApiResponse.Response<T> responseObj = new PublicDataApiResponse.Response<>();
            apiResponse.setResponse(responseObj);
            
            // Header 파싱 (cmmMsgHeader에서 오류 정보 추출)
            PublicDataApiResponse.Header header = new PublicDataApiResponse.Header();
            
            // 오류 메시지 확인
            if (response.contains("<errMsg>")) {
                String errMsg = extractXmlValue(response, "errMsg");
                header.setResultMsg(errMsg);
                log.debug("📊 XML errMsg: {}", errMsg);
                
                // 오류가 있으면 에러 코드 설정
                if (!"OK".equals(errMsg) && !"NORMAL SERVICE".equals(errMsg)) {
                    header.setResultCode("ERROR");
                } else {
                    header.setResultCode("000");
                }
            }
            
            // returnAuthMsg 확인
            if (response.contains("<returnAuthMsg>")) {
                String returnAuthMsg = extractXmlValue(response, "returnAuthMsg");
                log.debug("📊 XML returnAuthMsg: {}", returnAuthMsg);
            }
            
            // returnReasonCode 확인
            if (response.contains("<returnReasonCode>")) {
                String returnReasonCode = extractXmlValue(response, "returnReasonCode");
                log.debug("📊 XML returnReasonCode: {}", returnReasonCode);
            }
            
            responseObj.setHeader(header);
            
            // Body 파싱
            PublicDataApiResponse.Body<T> body = new PublicDataApiResponse.Body<>();
            PublicDataApiResponse.Items<T> items = new PublicDataApiResponse.Items<>();
            List<T> itemList = new ArrayList<>();
            
            // 실제 데이터가 있는지 확인 (items 태그 또는 실제 데이터 라인)
            if (response.contains("<items>") || response.contains("<item>")) {
                // 기존 XML 파싱 로직 사용
                if (response.contains("<item>")) {
                    String[] itemParts = response.split("<item>");
                    for (int i = 1; i < itemParts.length; i++) {
                        String itemXml = itemParts[i];
                        if (itemXml.contains("</item>")) {
                            itemXml = "<item>" + itemXml.substring(0, itemXml.indexOf("</item>") + 7);
                            T item = parseXmlItem(itemXml, responseType);
                            if (item != null) {
                                itemList.add(item);
                            }
                        }
                    }
                }
            } else {
                // 텍스트 형식 데이터 파싱 (실제 API 응답)
                String[] lines = response.split("\n");
                for (String line : lines) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("<") && !line.startsWith("000")) {
                        T item = parseTextItem(line, responseType);
                        if (item != null) {
                            itemList.add(item);
                        }
                    }
                }
            }
            
            body.setNumOfRows(itemList.size());
            body.setPageNo(1);
            body.setTotalCount(itemList.size());
            items.setItem(itemList);
            body.setItems(items);
            responseObj.setBody(body);
            
            log.debug("✅ 공공데이터 포털 XML 응답 파싱 완료 - 총 {}개 아이템", itemList.size());
            return apiResponse;
            
        } catch (Exception e) {
            log.error("❌ 공공데이터 포털 XML 응답 파싱 중 예외 발생", e);
            throw new PublicDataApiException("XML_PARSE_ERROR", "공공데이터 포털 XML 응답 파싱 중 오류 발생: " + e.getMessage(), e);
        }
    }
    
    /**
     * XML item 태그를 ApartmentRentItem으로 파싱
     */
    private <T> T parseXmlItem(String itemXml, Class<T> responseType) {
        try {
            if (responseType == ApartmentRentItem.class) {
                ApartmentRentItem item = new ApartmentRentItem();
                
                // XML에서 각 필드 값을 추출
                item.setAptNm(extractXmlValue(itemXml, "aptNm"));
                item.setBuildYear(extractXmlValue(itemXml, "buildYear"));
                item.setContractTerm(extractXmlValue(itemXml, "contractTerm"));
                item.setContractType(extractXmlValue(itemXml, "contractType"));
                item.setDealDay(extractXmlValue(itemXml, "dealDay"));
                item.setDealMonth(extractXmlValue(itemXml, "dealMonth"));
                item.setDealYear(extractXmlValue(itemXml, "dealYear"));
                item.setDeposit(extractXmlValue(itemXml, "deposit"));
                item.setExcluUseAr(extractXmlValue(itemXml, "excluUseAr"));
                item.setFloor(extractXmlValue(itemXml, "floor"));
                item.setJibun(extractXmlValue(itemXml, "jibun"));
                item.setMonthlyRent(extractXmlValue(itemXml, "monthlyRent"));
                item.setPreDeposit(extractXmlValue(itemXml, "preDeposit"));
                item.setPreMonthlyRent(extractXmlValue(itemXml, "preMonthlyRent"));
                item.setSggCd(extractXmlValue(itemXml, "sggCd"));
                item.setUmdNm(extractXmlValue(itemXml, "umdNm"));
                item.setUseRRRight(extractXmlValue(itemXml, "useRRRight"));
                
                log.debug("📋 XML item 파싱 완료: {}", item.getAptNm());
                return (T) item;
            }
            
            return null;
        } catch (Exception e) {
            log.error("❌ XML item 파싱 중 오류: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 실제 API 응답 텍스트 라인을 ApartmentRentItem으로 파싱
     * 형식: "아파트명\t건축년도\t계약일\t월세금액\t전용면적\t층\t지번\t법정동\t지역코드"
     */
    private <T> T parseTextItem(String line, Class<T> responseType) {
        try {
            if (responseType == ApartmentRentItem.class) {
                String[] fields = line.split("\t");
                if (fields.length >= 9) {
                    ApartmentRentItem item = new ApartmentRentItem();
                    
                    item.setAptNm(fields[0]);                    // 아파트명
                    item.setBuildYear(fields[1]);                // 건축년도
                    
                    // 계약일 파싱 (YYYYMMDD 형식)
                    String contractDate = fields[2];
                    if (contractDate.length() >= 8) {
                        item.setDealYear(contractDate.substring(0, 4));
                        item.setDealMonth(contractDate.substring(4, 6));
                        item.setDealDay(contractDate.substring(6, 8));
                    }
                    
                    item.setMonthlyRent(fields[3]);              // 월세금액
                    item.setExcluUseAr(fields[4]);               // 전용면적
                    item.setFloor(fields[5]);                    // 층
                    item.setJibun(fields[6]);                    // 지번
                    item.setUmdNm(fields[7]);                    // 법정동
                    item.setSggCd(fields[8]);                    // 지역코드
                    
                    log.debug("📋 텍스트 아이템 파싱 완료: {}", item.getAptNm());
                    return (T) item;
                }
            }
            
            return null;
        } catch (Exception e) {
            log.error("❌ 텍스트 아이템 파싱 중 오류: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * XML에서 특정 태그의 값을 추출하는 간단한 메서드
     */
    private String extractXmlValue(String xml, String tagName) {
        String startTag = "<" + tagName + ">";
        String endTag = "</" + tagName + ">";
        
        int startIndex = xml.indexOf(startTag);
        if (startIndex == -1) {
            return null;
        }
        
        int endIndex = xml.indexOf(endTag, startIndex);
        if (endIndex == -1) {
            return null;
        }
        
        return xml.substring(startIndex + startTag.length(), endIndex).trim();
    }
    
    /**
     * 응답 헤더 검증
     */
    private void validateResponseHeader(PublicDataApiResponse.Header header) {
        String resultCode = header.getResultCode();
        String resultMsg = header.getResultMsg();
        
        log.info("📊 API 응답 코드: {}, 메시지: {}", resultCode, resultMsg);
        
        PublicDataErrorCode errorCode = PublicDataErrorCode.fromCode(resultCode);
        
        if (errorCode.isError()) {
            log.error("❌ API 오류 발생 - 코드: {}, 메시지: {}", resultCode, resultMsg);
            throw new PublicDataApiException(resultCode, resultMsg);
        }
    }
    
    /**
     * API 엔드포인트 URL 생성
     */
    public String buildApiUrl(String endpoint) {
        return baseUrl + endpoint;
    }
    
    /**
     * 서비스키 유효성 검증
     */
    public boolean isValidServiceKey() {
        return serviceKey != null && !serviceKey.trim().isEmpty();
    }
}
