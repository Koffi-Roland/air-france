package com.afklm.spring.security.habile.properties;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Ss4hProperties.class)
@ActiveProfiles("emptyConfiguration")
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(initializers = {YamlFileApplicationContextInitializer.class})
public class Ss4hEmptyPropertiesTest {

    @Autowired
    private Ss4hProperties props;

    @BeforeAll
    public static void setupYml() {
        YamlFileApplicationContextInitializer.ymlFilename = "application-empty.yml";
    }

    @Test
    public void testProperties() {
        assertThat(props).isNotNull();
        assertThat(props.getActuatorsRole()).isNull();
        assertThat(props.getRoles().size()).isEqualTo(0);
        assertThat(props.getAnonymousRoles().size()).isEqualTo(0);
        assertThat(props.getUrlsHttpVerbsAuthorities().size()).isEqualTo(0);
        assertThat(props.getContentSecurityPolicy()).isNull();
    }

}
