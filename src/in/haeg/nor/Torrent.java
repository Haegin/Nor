package in.haeg.nor;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

public class Torrent {
    private final Object[] TORRENT_PARAMS;

    private String         m_Hash;
    private XmlRpcClient   m_Connection;

    public Torrent(XmlRpcClient a_Connection, String a_Hash) {
        m_Hash = a_Hash;
        m_Connection = a_Connection;
        Object[] params = { a_Hash };
        TORRENT_PARAMS = params;
    }

    public String getHash() {
        return m_Hash;
    }

    public String getName() throws XmlRpcException {
        return (String) m_Connection.execute("d.get_name", TORRENT_PARAMS);

    }

    public String getDirectory() throws XmlRpcException {
        return (String) m_Connection.execute("d.get_directory", TORRENT_PARAMS);
    }

    public String getType() throws XmlRpcException {
        String[] custom1 = ((String) m_Connection.execute("d.get_custom1", TORRENT_PARAMS)).split("/"); // This gives us the path the file will be moved to when finished as an array of directories.
        return custom1[custom1.length - 1]; // This chops off the last directory which is named according to the type.
        // TODO: This works well with my setup but could be improved by setting custom1 to store the type and calculate the path based off that.
    }

    public String getCustom1() throws XmlRpcException {
        return (String) m_Connection.execute("d.get_custom1", TORRENT_PARAMS);
    }

    public Map<Integer, TorrentFile> getFiles() throws XmlRpcException {
        HashMap<Integer, TorrentFile> files = new HashMap<Integer, TorrentFile>();

        Object[] params = { m_Hash, "", "f.get_path=" }; // Fails as it expects the second parameter to be the number of the file we're working on for some reason.
        Object[] returned = (Object[]) m_Connection.execute("f.multicall", params);
        Object[] fileInfo;
        String filePath;
        for (int i = 0; i < returned.length; ++i) {
            fileInfo = (Object[]) returned[i];
            filePath = (String) fileInfo[0];
            files.put(i, new TorrentFile(m_Connection, this, filePath, i));
        }
        return files;
    }

    public TorrentFile getFile(Integer a_FileNumb) throws XmlRpcException {
        Object[] params = { m_Hash, a_FileNumb };
        return new TorrentFile(m_Connection, this, (String) m_Connection.execute("f.get_path", params), a_FileNumb);
    }

    public void stop() throws XmlRpcException {
        m_Connection.execute("d.stop", TORRENT_PARAMS);
    }

    public void start() throws XmlRpcException {
        m_Connection.execute("d.start", TORRENT_PARAMS);
    }

    public void setDirectory(String a_Directory) throws XmlRpcException {
        Object[] params = { m_Hash, a_Directory };
        m_Connection.execute("d.set_directory", params);
    }
}
