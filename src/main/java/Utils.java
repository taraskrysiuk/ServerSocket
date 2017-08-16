import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Utils {

    public static byte[] readRequest(InputStream input) throws IOException {
        byte[] buff = new byte[8192];
        int hLen = 0;
        while (true){
            int count = input.read(buff, hLen, buff.length - hLen);
            if (count < 0){
                throw new RuntimeException("Connection is closed.");
            } else {
                hLen += count;
                if (hLen == buff.length){
                    throw new RuntimeException("Request is too long.");
                }
                if(isRequestEnd(buff, hLen)){
                    return Arrays.copyOfRange(buff, 0, hLen);
                }

            }
        }

    }

    private static boolean isRequestEnd(byte[] request, int length) {
        if (length < 4){
            return false;
        }
        return request[length] == 0 &&
                request[length + 1] == 0;
    }

    public static byte[] makeResponse(String responseBody) throws UnsupportedEncodingException {
        StringBuilder r = new StringBuilder();
        r.append("HTTP/1.1 200 OK\r\n");
        r.append("Content-Type: text/html\r\n\r\n");
        r.append(responseBody);
        return r.toString().getBytes("US-ASCII");
    }

}
