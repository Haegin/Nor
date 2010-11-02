package in.haeg.nor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class RTorrentConnection {

    private static final Object[]  NULL_PARAMS = {};

    private XmlRpcClient           m_Client;
    private XmlRpcClientConfigImpl m_Config;
    private List<Torrent>          m_Torrents;

    public RTorrentConnection(String a_URL) {
        m_Client = new XmlRpcClient();
        m_Config = new XmlRpcClientConfigImpl();
        try {
            m_Config.setServerURL(new URL(a_URL));
            m_Client.setConfig(m_Config);

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
    }

    /* Getters */
    public int getUploadLimit() throws XmlRpcException {
        return (Integer) m_Client.execute("get_upload_rate", NULL_PARAMS);
    }

    public int getUploadSpeed() throws XmlRpcException {
        return (Integer) m_Client.execute("get_up_rate", NULL_PARAMS);
    }

    public int getDownloadLimit() throws XmlRpcException {
        return (Integer) m_Client.execute("get_download_rate", NULL_PARAMS);
    }

    public int getDownloadSpeed() throws XmlRpcException {
        return (Integer) m_Client.execute("get_down_rate", NULL_PARAMS);
    }

    public String getSessionDir() throws XmlRpcException {
        return (String) m_Client.execute("get_session", NULL_PARAMS);
    }

    public String getIP() throws XmlRpcException {
        return (String) m_Client.execute("get_ip", NULL_PARAMS);
    }

    public String getHttpProxy() throws XmlRpcException {
        return (String) m_Client.execute("get_http_proxy", NULL_PARAMS);
    }

    public String getPortRange() throws XmlRpcException {
        return (String) m_Client.execute("get_port_range", NULL_PARAMS);
    }

    public String getDirectory() throws XmlRpcException {
        return (String) m_Client.execute("get_directory", NULL_PARAMS);
    }

    public List<Torrent> getTorrents(String a_ViewName) throws XmlRpcException {
        Object[] params = { a_ViewName, "d.get_hash=" };
        List<Torrent> torrentList = new LinkedList<Torrent>();
        Object[] returned = (Object[]) m_Client.execute("d.multicall", params);
        Object[] torrentInfo;
        String torrentHash;
        for (Object obj : returned) {
            torrentInfo = (Object[]) obj;
            torrentHash = (String) torrentInfo[0];
            torrentList.add(new Torrent(m_Client, torrentHash));
        }
        return torrentList;
    }

    public Torrent getTorrent(String a_Hash) throws XmlRpcException {
        HashMap<String, Torrent> torrents = new HashMap<String, Torrent>();
        for (Torrent torrent : getTorrents("name")) {
            torrents.put(torrent.getHash(), torrent);
        }
        return torrents.get(a_Hash);
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
