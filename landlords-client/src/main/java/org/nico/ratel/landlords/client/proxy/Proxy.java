package org.nico.ratel.landlords.client.proxy;

import java.net.URISyntaxException;

public interface Proxy {

    void connect(String serverAddress, int port) throws InterruptedException, URISyntaxException;
}
