package com.vinesmario.common.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

public class MacUtil {

    public static void main(String[] args) {
        ByteBuffer content = ByteBuffer.allocate(4);
        content.putInt(10100000);
        //MAC校验码
        byte[] macKey = macKey = hexStringToBytes("009a1d2000000000");
        //Mac校验
        if (!bytes2HexString(macCode(content)).equals(bytes2HexString(macKey))) {
        } else {
        }


        String os = getOSName();
        System.out.println("os: " + os);
        if (os.startsWith("windows")) {
            String mac = getWindowsMACAddress();
            System.out.println("mac: " + mac);
        } else if (os.startsWith("linux")) {
            String mac = getLinuxMACAddress();
            System.out.println("mac: " + mac);
        } else {
            String mac = getUnixMACAddress();
            System.out.println("mac: " + mac);
        }
    }

    public static String MACString(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return bytes2HexString(macCode(byteBuffer)).toUpperCase();
    }

    /**
     * MAC码的生成 返回byte数组
     *
     * @param buff
     * @return
     */
    public static byte[] macCode(ByteBuffer buff) {
        int length = buff.limit() / 8;//ByteBuffer长度除8后的整数
        int remainder = buff.limit() % 8;//ByteBuffer长度除8后的余数
        int count = length + 1;// 表示有几段8字节的ByteBuffer
        if (remainder == 0) {
            count = length;
        }
        byte[] temp = null;//用来保存每8字节的ByteBuffer值
        byte[] macByte = new byte[8];//MAC 验证码
        buff.position(0);
        if (count > 1) {//若ByteBuffer长度大于8字节
            for (int i = 0; i < count; i++) {
                temp = new byte[8];
                if (i == count - 1 && remainder > 0) {//最后一段ByteBuffer，不足时补0
                    for (int k = 0; k < remainder; k++) {
                        temp[k] = buff.get();
                        buff.mark();
                    }
                    for (int k = remainder; k < 8; k++) {
                        temp[k] = 0;
                    }
                } else {//直接取ByteBuffer的值存入temp数组
                    temp = new byte[]{buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get()};
                }
                buff.mark();//标识ByteBuffer的当前位置
                if (i == 0) {
                    macByte = temp;
                } else {//当ByteBuffer有二个或以上8字节时，进行异或
                    macByte = new byte[]{(byte) (macByte[0] ^ temp[0]), (byte) (macByte[1] ^ temp[1]), (byte) (macByte[2] ^ temp[2]), (byte) (macByte[3] ^ temp[3])
                            , (byte) (macByte[4] ^ temp[4]), (byte) (macByte[5] ^ temp[5]), (byte) (macByte[6] ^ temp[6]), (byte) (macByte[7] ^ temp[7])};
                }
            }
        } else {
            macByte = new byte[8];
            for (int i = 0; i < buff.limit(); i++) {
                macByte[i] = buff.get();
            }
            for (int i = buff.limit(); i < macByte.length; i++) {
                macByte[i] = 0;
            }
        }
        return macByte;
    }

    /**
     * 把bytes数据转换成对应16进制的String型字符串
     *
     * @param data
     * @return String
     */
    public static String bytes2HexString(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(byte2HexString(data[i]));
        }
        return sb.toString();
    }

    /**
     * 把byte转换成对应16进制的String型字符串
     *
     * @param data
     * @return String
     */
    private static String byte2HexString(byte data) {
        StringBuffer buf = new StringBuffer(2);
        if (((byte) data & 0xff) < 0x10) {
            buf.append("0");
        }
        buf.append(Integer.toString((int) data & 0xff, 16));
        return buf.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取Unix网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[hwaddr]
                 */
                index = line.toLowerCase().indexOf("hwaddr");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 获取Linux网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("硬件地址");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + 4).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        // 取不到，试下Unix取发
        if (mac == null) {
            return getUnixMACAddress();
        }

        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     *
     * @return mac地址
     */
    public static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * windows下的命令，显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[physical address]
                 */
//				index = line.toLowerCase().indexOf("physical address");
//				if (index != -1) {
                if (line.split("-").length == 6) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        /**
                         * 取出mac地址并去除2边空格
                         */
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
                index = line.toLowerCase().indexOf("物理地址");
                if (index != -1) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        /**
                         * 取出mac地址并去除2边空格
                         */
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    public static String getMac() {
        String os = getOSName();
        String mac;
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else if (os.startsWith("linux")) {
            mac = getLinuxMACAddress();
        } else {
            mac = getUnixMACAddress();
        }
        return mac == null ? "" : mac;
    }
}
