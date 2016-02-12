package connection;

/**
 * Created by Snsepro50 on 4/1/2015.
 */
public class session {

    public static String internet_connction;
    public static String internet_weak;
    public static String internet_responce = "";

    // ******** For INTERNET ***********
    public static String get_internet_connction() {
        return internet_connction;
    }

    public void set_internet_connction(String connction) {
        internet_connction = connction;
    }

    // ******** For INTERNET Connection timeout ***********
    public static String get_internet_weak() {
        return internet_weak;
    }

    public void set_internet_weak(String res) {
        internet_weak = res;
    }

    // ******** For INTERNET ***********
    public String get_internet_responce() {
        return internet_responce;
    }

    public void set_internet_responce(String responce) {
        internet_responce = responce;
    }
}
