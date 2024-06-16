package com.afklm.spring.security.habile.oidc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

class JwtUtilsTest {

    private static String TOKEN = "eyJhbGciOiJSUzUxMiIsImtpZCI6ImNlcnRkZWZhdWx0MDAxIiwicGkuYXRtIjoiMiJ9.eyJzY29wZSI6WyJvcGVuaWQiLCJhZGRyZXNzIiwiZW1haWwiLCJwaG9uZSIsInByb2ZpbGUiXSwiY2xpZW50X2lkIjoiY2xpZW50X3BhX2NhZV9hZl9pbnRyIiwiaXNzIjoiaHR0cHM6Ly9mZWRodWItY2FlLmFpcmZyYW5jZWtsbS5jb20iLCJhdWQiOiJEZWZhdWx0R2xvYmFsIiwiYXV0aGxldmVsIjoidXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6UGFzc3dvcmQiLCJwaS5zcmkiOiI5d25PcFJ3YzhlckswNWJrRW90VWNCZU9NRHMuVkVrLm0tZkQiLCJzdWIiOiJtNDA4NDYxIiwiZXhwIjoxNjIwODMzMTUzfQ.M44xAKnoPK53QKvrJUki1gNSEYbh514JHLKcBe7F4u8G3mxQQ_vQFWRuKf11vHPqX3pexk9ESug5xWm3hON7Xfg_f5mVBXG3u7eiFBwMKU8il_1pdIChRRxCEJznHChfnAS8_bOd1brXvxmJDlnkOfvnFmJJU2zcimL2ehk9WCsFi6c26GwapxtLw_PgDea_ypZxBZT5S1Q_rqcqGJUAtrmAP64mS-Za64O8JUyiWjs90F2B1o1nK6hJLhnqDVKcaCEFIErH7Fk-6EnFatw0sJI6lUYEG3DDXOEBzEsmnIS68F2ThkVktKtWb5Q6tH_A87ojNk2BQIEDtXY1_VDV0A";

    @Test
    @DisplayName("test extraction of an existing field")
    void testExtractionOfAnExistingField() {
        String key = "iss";
        assertThat(JwtUtils.getElementFromBody(TOKEN, key)).isNotEmpty();
        Optional<String> elementAsString = JwtUtils.getStringElementFromBody(TOKEN, key);
        assertThat(elementAsString.isPresent()).isTrue();
        assertThat(elementAsString.get()).isEqualTo("https://fedhub-cae.airfranceklm.com");
    }

    @Test
    @DisplayName("test extraction of non existing field")
    void testExtractionOfNonExistingField() {
        String key = "iss2";
        assertThat(JwtUtils.getElementFromBody(TOKEN, key)).isEmpty();
        assertThat(JwtUtils.getStringElementFromBody(TOKEN, key)).isEmpty();
    }

    @Test
    @DisplayName("test extraction of non string element as string produces empty optional")
    void testExtractionOfNonStringElementAsStringProducesEmptyOptional() {
        String key = "scope";
        assertThat(JwtUtils.getElementFromBody(TOKEN, key)).isNotEmpty();
        assertThat(JwtUtils.getStringElementFromBody(TOKEN, key)).isEmpty();
    }
}