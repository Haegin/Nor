package in.haeg.nor;

public class Main {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String url = "http://127.0.0.1/RPC2";
        System.out.println(url);
        RTorrentConnection conn = new RTorrentConnection(url);

        try {
            System.out.println("The download rate is: " + conn.getDownloadRate());
            System.out.println("The upload rate is: " + conn.getUploadRate());
            System.out.println("The session dir is: " + conn.getSessionDir());
            System.out.println("The IP is: " + conn.getIP());
            System.out.println("The port range is: " + conn.getPortRange());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
