
public class HttpAuthBasic {

    /**
     * max timestamp
     */
    private static final int MAX_TIME = 60000;
    /**
     * UID max length
     */
    private static final int UID_MAX_LENGTH = 36;

    private static final String CLIENT_SALT = "@%^";

    public String auth(String appKey, String method, String sign, String timestamp, String uid, String clientIP) {
        String ipPattern = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
        Long currentTime = System.currentTimeMillis();
        Long time = Long.parseLong(timestamp);
        long div = currentTime - time;
        if (currentTime - Long.parseLong(timestamp) < MAX_TIME) {
            if (secret != null) {
                String secret = "do something - getSecretByAppKey";
                // need org.apache.shiro.crypto.hash.Md5Hash package
                if (!sign.equals(new Md5Hash(secret + CLIENT_SALT + timestamp).toString())) {
                    // UNAUTHORIZED
                } else {
                    // do something
                }
            } else {
                // FORBIDDEN
            }
        } else {
            // BAD_REQUEST
        }
    }

}
