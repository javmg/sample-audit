package com.jmgits.sample.audit.util;

import com.jmgits.sample.audit.view.TokenData;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Unit testing for {@link SecurityUtils}
 */
public class SecurityUtilsTest {

    @Test
    public void testSetAndGetDataOk() {

        TokenData tokenData = new TokenData();
        tokenData.setUsername("myUsername");
        tokenData.setAuthorities(Collections.singleton("myAuthority"));

        //
        // business

        SecurityUtils.setTokenData(tokenData);

        TokenData result = SecurityUtils.getTokenData();

        assertThat(result, allOf(
                hasProperty("username", is(result.getUsername())),
                hasProperty("authorities", is(result.getAuthorities()))
        ));

    }
}
