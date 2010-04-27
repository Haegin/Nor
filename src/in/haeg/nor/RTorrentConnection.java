package in.haeg.nor;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;

public class RTorrentConnection {
    XmlRpcClient           m_Client;
    XmlRpcClientConfigImpl m_Config;

    public RTorrentConnection(String a_URL) {
        m_Config = new XmlRpcClientConfigImpl();
        try {
            m_Config.setServerURL(new URL(a_URL));
            m_Client.setTransportFactory(new XmlRpcCommonsTransportFactory(m_Client));
            m_Client.setConfig(m_Config);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

}
