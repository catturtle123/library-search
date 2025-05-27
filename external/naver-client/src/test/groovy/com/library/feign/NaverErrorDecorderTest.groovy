package com.library.feign

import com.fasterxml.jackson.databind.ObjectMapper
import com.library.NaverErrorResponse
import feign.Request
import feign.Response
import spock.lang.Specification

class NaverErrorDecorderTest extends Specification {

    ObjectMapper objectMapper = Mock()

    NaverErrorDecorder naverErrorDecorder = new NaverErrorDecorder(objectMapper);

    def "에러디코더에서 에러발생시 RuntimeException 예외가 던져진다"() {


        given:
        def responseBody = Mock(Response.Body)
        def inputStream = new ByteArrayInputStream()
        def response = Response.builder()
                .status(400)
        .request(Request.create(Request.HttpMethod.GET, "testUrl", [:], null as Request.Body, null))
                .body(responseBody)
                .build()

        1 * responseBody.asInputStream() >> inputStream
        1 * objectMapper.readValue(*_) >> new NaverErrorResponse("SE03", "error!!")

        when:
        naverErrorDecorder.decode(_ as String, response)

        then:
        RuntimeException e = thrown()
        e.message == "error!!"
    }
}
