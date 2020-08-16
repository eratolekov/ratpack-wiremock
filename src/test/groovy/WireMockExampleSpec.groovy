import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomjankes.wiremock.WireMockGroovy
import org.junit.Rule
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse
import ratpack.test.exec.ExecHarness
import spock.lang.AutoCleanup
import spock.lang.Specification

class WireMockExampleSpec extends Specification {

    @Rule
    WireMockRule wireMockRule = new WireMockRule(WireMockConfiguration.options().dynamicPort())

    @AutoCleanup
    ExecHarness execHarness = ExecHarness.harness(1)

    void "ratpack httpclient"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/"
            }
            response {
                status(200)
                headers {
                    "Content-Type" "text/plain"
                    "Connection" "close"
                }
                body('123'.padRight(1*1000,"x"))
            }
        }

        and:
        HttpClient httpClient = HttpClient.of { httpClientSpec ->
            httpClientSpec.poolSize(10)
                    .maxContentLength(2000000)
                    .responseMaxChunkSize(16384)
        };


        when:
        ReceivedResponse response = execHarness.yield {
            httpClient.get(new URI("http://localhost:${wireMockRule.port()}"))
        }.getValueOrThrow()

        then:
        response.body.bytes.size() ==  1000
    }

    void "groovy httpclient"() {
        given:
        def wireMock = new WireMockGroovy(wireMockRule.port())
        wireMock.stub {
            request {
                method "GET"
                url "/"
            }
            response {
                status(200)
                headers {
                    "Content-Type" "text/plain"
                    "Connection" "close"
                }
                body('123'.padRight(1*1000,"x"))
            }
        }

        and:
        def resp = new URL("http://localhost:${wireMockRule.port()}").getText()


        expect:
        resp.length() == 1000
    }

}
