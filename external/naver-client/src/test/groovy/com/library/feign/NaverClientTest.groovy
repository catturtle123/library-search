package com.library.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.test.context.ActiveProfiles
import spock.lang.Ignore
import spock.lang.Specification

@Ignore // 외부 연동 테스트는 비결정적인 테스트 -> 무시 처리
@SpringBootTest(classes = NaverClientTest.TestConfig.class)
@ActiveProfiles("test")
class NaverClientTest extends Specification {

    @EnableAutoConfiguration
    @EnableFeignClients(clients = NaverClient.class)
    static class TestConfig{}

    @Autowired
    NaverClient naverClient

    def "naver 호출"() {
        given:


        when:
        def response = naverClient.searchBook("HTTP", 1, 10)

        then:
        print response
    }
}
