package pl.edu.pwr.bilbords.factories;

import java.io.*;
import java.net.*;
import java.rmi.server.*;
import javax.net.ssl.*;

public class RMISSLClientSocketFactory
        implements RMIClientSocketFactory, Serializable {

    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory factory =
                (SSLSocketFactory)SSLSocketFactory.getDefault();
        return factory.createSocket(host, port);
    }

    public int hashCode() {
        return getClass().hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else return obj != null && getClass() == obj.getClass();
    }
}
