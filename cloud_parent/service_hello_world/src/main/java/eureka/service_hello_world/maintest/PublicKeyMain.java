package eureka.service_hello_world.maintest;

import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
/**
 * Created by tangminyan on 2019/9/5.
 */
public class PublicKeyMain {
    public static void main(String[] args) throws Exception {
        URL url = new URL("https://www.qingshuihe.gov.cn");
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();
        //会拿到完整的证书链
        X509Certificate cert = (X509Certificate)certs[0];
        //cert[0]是证书链的最下层
        System.out.println("序号：" + cert.getSerialNumber());
        System.out.println("颁发给：" + cert.getSubjectDN().getName());
        System.out.println("颁发者：" + cert.getIssuerDN().getName());
        System.out.println("起始：" + cert.getNotBefore());
        System.out.println("过期：" + cert.getNotAfter());
        System.out.println("算法：" + cert.getSigAlgName());
        System.out.println("指纹：" + getThumbPrint(cert));
        conn.disconnect();
    }
    private static String getThumbPrint(X509Certificate cert) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] der = cert.getEncoded();
        md.update(der);
        byte[] digest = md.digest();
        return bytesToHexString(digest);
    }
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
