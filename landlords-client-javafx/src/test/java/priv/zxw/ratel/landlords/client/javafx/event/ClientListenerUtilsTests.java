package priv.zxw.ratel.landlords.client.javafx.event;

import org.junit.Ignore;
import org.nico.ratel.landlords.enums.ClientEventCode;
import priv.zxw.ratel.landlords.client.javafx.listener.ClientListenerUtils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class ClientListenerUtilsTests {

    /**
     * always fail
     *
     * 因为junit启动时的classpath为target/test-classes，而不是target/classes
     */
    @Ignore
    public void testLoadListener() {
        ClientEventCode connectListenerCode = ClientEventCode.CODE_CLIENT_CONNECT;

        assertThat(ClientListenerUtils.getListener(connectListenerCode), notNullValue());
    }
}
