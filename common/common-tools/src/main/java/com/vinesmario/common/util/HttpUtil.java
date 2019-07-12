package com.vinesmario.common.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    static {
        System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
        System.setProperty("sun.net.client.defaultReadTimeout", "30000");
    }

    public static String get(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
            //得到输入流
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while (-1 != (len = bis.read(buffer))) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
            return baos.toString("utf-8");
        }
        return null;
    }

    public static String post(String urlStr, String jsonStr) throws IOException, ClassNotFoundException {
        URL url = new URL(urlStr);
        // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,
        // 故此处最好将其转化为HttpURLConnection类型的对象,以便用到
        // HttpURLConnection更多的API.如下:
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        httpURLConnection.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        httpURLConnection.setDoInput(true);
        // Post 请求不能使用缓存
        httpURLConnection.setUseCaches(false);
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
        httpURLConnection.setRequestProperty("Content-type", "application/x-java-serialized-object");
        // 设定请求的方法为"POST"，默认是GET
        httpURLConnection.setRequestMethod("POST");
        // 连接，从上述第2条中url.openConnection()至此的配置必须要在connect之前完成，
        httpURLConnection.connect();
        // 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
        // 所以在开发中不调用上述的connect()也可以)。
        PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
        // 发送请求参数
        printWriter.write(jsonStr);
        // 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
        printWriter.flush();
        // 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
        printWriter.close();
        // 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
        //得到响应码
        if (HttpURLConnection.HTTP_OK == httpURLConnection.getResponseCode()) {
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while (-1 != (len = bis.read(buffer))) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        }
        return null;
    }

}
