package in.haeg.nor;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;

public class Torrent {
    private final Object[] TORRENT_PARAMS;

    private String         m_Hash;
    private String         m_Name;
    private XmlRpcClient   m_Connection;

    public Torrent(XmlRpcClient a_Connection, String a_Hash, String a_Name) {
        m_Hash = a_Hash;
        m_Name = a_Name;
        m_Connection = a_Connection;
        Object[] params = { a_Hash };
        TORRENT_PARAMS = params;
    }

    public String getHash() {
        return m_Hash;
    }

    public String getName() {
        return m_Name;
    }

    public String getDirectory() throws XmlRpcException {
        return (String) m_Connection.execute("d.get_directory", TORRENT_PARAMS);
    }

    public Map<Integer, File> getFiles() throws XmlRpcException {
        HashMap<Integer, File> files = new HashMap<Integer, File>();

        Object[] params = { m_Hash, "", "f.get_path=" }; // Fails as it expects the second parameter to be the number of the file we're working on for some reason.
        Object[] returned = (Object[]) m_Connection.execute("f.multicall", params);
        Object[] fileInfo;
        String filePath;
        for (int i = 0; i < returned.length; ++i) {
            fileInfo = (Object[]) returned[i];
            filePath = (String) fileInfo[0];
            files.put(i, new File(m_Connection, this, filePath, i));
        }
        return files;
    }
}
