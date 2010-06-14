package in.haeg.nor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class RTorrentConnection {

    private static final Object[]  nullParams = {};

    private XmlRpcClient           m_Client;
    private XmlRpcClientConfigImpl m_Config;
    private List<Torrent>          m_Torrents;

    public RTorrentConnection(String a_URL) {
        m_Client = new XmlRpcClient();
        m_Config = new XmlRpcClientConfigImpl();
        try {
            m_Config.setServerURL(new URL(a_URL));
            m_Client.setConfig(m_Config);

            Object[] params = { "name", "d.get_hash=", "d.get_directory=", "d.get_name=" };
            try {
                Object[] returned = (Object[]) m_Client.execute("d.multicall", params);
                Object[] torrentInfo;
                String torrentHash, torrentName, torrentDirectory;
                for (Object obj : returned) {
                    torrentInfo = (Object[]) obj;
                    torrentHash = (String) torrentInfo[0];
                    torrentDirectory = (String) torrentInfo[1];
                    torrentName = (String) torrentInfo[2];
                    m_Torrents.add(new Torrent(torrentHash, torrentName, torrentDirectory));
                }
            } catch (XmlRpcException ex) {
                ex.printStackTrace();
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    /* Getters */
    public int getUploadRate() throws XmlRpcException {
        return (Integer) m_Client.execute("get_upload_rate", nullParams);
    }

    public int getUploadTotal() throws XmlRpcException {
        return (Integer) m_Client.execute("to_mb=$get_up_total", nullParams);
    }

    public int getDownloadRate() throws XmlRpcException {
        return (Integer) m_Client.execute("get_download_rate", nullParams);
    }

    public int getDownloadTotal() throws XmlRpcException {
        return (Integer) m_Client.execute("to_mb=$get_down_total", nullParams);
    }

    public String getSessionDir() throws XmlRpcException {
        return (String) m_Client.execute("get_session", nullParams);
    }

    public String getIP() throws XmlRpcException {
        return (String) m_Client.execute("get_ip", nullParams);
    }

    public String getHttpProxy() throws XmlRpcException {
        return (String) m_Client.execute("get_http_proxy", nullParams);
    }

    public String getPortRange() throws XmlRpcException {
        return (String) m_Client.execute("get_port_range", nullParams);
    }

    public String getDirectory() throws XmlRpcException {
        return (String) m_Client.execute("get_directory", nullParams);
    }

    /* Setters */
    public void setUploadRate(int rate) throws XmlRpcException {
        Object[] params = { rate };
        m_Client.execute("set_upload_rate", params);
    }

    public void setDownloadRate(int rate) throws XmlRpcException {
        Object[] params = { rate };
        m_Client.execute("set_download_rate", params);
    }

}
