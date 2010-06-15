package in.haeg.nor;

import org.apache.xmlrpc.client.XmlRpcClient;

public class File {

    private final Object[] FILE_PARAMS;

    private Torrent        m_Torrent;
    private String         m_FilePath;
    private Integer        m_FileNumber;
    private XmlRpcClient   m_Connection;

    public File(XmlRpcClient a_Connection, Torrent a_Torrent, String a_FilePath, Integer a_FileNumber) {
        m_Connection = a_Connection;
        m_Torrent = a_Torrent;
        m_FilePath = a_FilePath;
        m_FileNumber = a_FileNumber;
        Object[] params = { a_Torrent.getHash(), m_FileNumber };
        FILE_PARAMS = params;
    }

    public String getPath() {
        return m_FilePath;
    }
}
