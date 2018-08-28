package com.haron.pro.common.util;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Created by martea on 2018/8/28.
 */
public class FileUtil {

    public static final String albumString = "<div class=\"col-sm-6 col-md-4\">\n" +
                            "\t\t                <div class=\"thumbnail\">\n" +
                            "\t\t                    <a class=\"lightbox\" href=\"url\">\n" +
                            "\t\t                        <img src=\"url\" alt=\"Park\">\n" +
                            "\t\t                    </a>\n" +
                            "\t\t                    <div class=\"caption\">\n" +
                            "\t\t                        <h3>lable</h3>\n" +
                            "\t\t                        <p>explain</p>\n" +
                            "\t\t                    </div>\n" +
                            "\t\t                </div>\n" +
                            "\t\t            </div>";

    /**
     * 读取文件内容
     *
     * @param pathName 文件路径
     * @return
     */
    public static String fileRead(String pathName) throws Exception {
        File file = new File(pathName);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }


    /**
     * 将字符串写入到文件
     * @param filePath 文件路径
     * @param fileContent 文件内容
     * @throws Exception
     */
    public static void WriteStringToFile(String filePath,String fileContent) throws Exception{
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(fileContent.getBytes());
            fos.close();
    }

        /**
         * 判断指定的文件是否存在。
         *
         * @param fileName
         * @return
         */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。
     * 注意：可能会在返回false的时候创建部分父目录。
     *
     * @param file
     * @return
     */
    public static boolean makeDirectory(File file) {
        File parent = file.getParentFile();
        if (parent != null) {
            return parent.mkdirs();
        }
        return false;
    }

    /**
     * 返回文件的URL地址。
     *
     * @param file
     * @return
     * @throws MalformedURLException
     */
    public static URL getURL(File file) throws MalformedURLException {
        String fileURL = "file:/" + file.getAbsolutePath();
        URL url = new URL(fileURL);
        return url;
    }

    /**
     * 从文件路径得到文件名。
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        File file = new File(filePath);
        return file.getName();
    }

    /**
     * 从文件名得到文件绝对路径。
     *
     * @param fileName
     * @return
     */
    public static String getFilePath(String fileName) {
        File file = new File(fileName);
        return file.getAbsolutePath();
    }

    /**
     * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
     *
     * @param filePath
     * @return
     */
    public static String toUNIXpath(String filePath) {
        return filePath.replace("", "/");
    }

    /**
     * 从文件名得到UNIX风格的文件绝对路径。
     *
     * @param fileName
     * @return
     */
    public static String getUNIXfilePath(String fileName) {
        File file = new File(fileName);
        return toUNIXpath(file.getAbsolutePath());
    }

    /**
     * 得到文件后缀名
     *
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 得到文件的名字部分。 实际上就是路径中的最后一个路径分隔符后的部分。
     *
     * @param fileName
     * @return
     */
    public static String getNamePart(String fileName) {
        int point = getPathLastIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return fileName;
        } else if (point == length - 1) {
            int secondPoint = getPathLastIndex(fileName, point - 1);
            if (secondPoint == -1) {
                if (length == 1) {
                    return fileName;
                } else {
                    return fileName.substring(0, point);
                }
            } else {
                return fileName.substring(secondPoint + 1, point);
            }
        } else {
            return fileName.substring(point + 1);
        }
    }

    /**
     * 得到文件名中的父路径部分。 对两种路径分隔符都有效。 不存在时返回""。
     * 如果文件名是以路径分隔符结尾的则不考虑该分隔符，例如"/path/"返回""。
     *
     * @param fileName
     * @return
     */
    public static String getPathPart(String fileName) {
        int point = getPathLastIndex(fileName);
        int length = fileName.length();
        if (point == -1) {
            return "";
        } else if (point == length - 1) {
            int secondPoint = getPathLastIndex(fileName, point - 1);
            if (secondPoint == -1) {
                return "";
            } else {
                return fileName.substring(0, secondPoint);
            }
        } else {
            return fileName.substring(0, point);
        }
    }

    /**
     * 得到路径分隔符在文件路径中最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName
     * @return
     */
    public static int getPathLastIndex(String fileName) {
        int point = fileName.lastIndexOf("/");
        if (point == -1) {
            point = fileName.lastIndexOf("");
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置前最后出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName
     * @param fromIndex
     * @return
     */
    public static int getPathLastIndex(String fileName, int fromIndex) {
        int point = fileName.lastIndexOf("/", fromIndex);
        if (point == -1) {
            point = fileName.lastIndexOf("", fromIndex);
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName
     * @return
     */
    public static int getPathIndex(String fileName) {
        int point = fileName.indexOf("/");
        if (point == -1) {
            point = fileName.indexOf("");
        }
        return point;
    }

    /**
     * 得到路径分隔符在文件路径中指定位置后首次出现的位置。 对于DOS或者UNIX风格的分隔符都可以。
     *
     * @param fileName
     * @param fromIndex
     * @return
     */
    public static int getPathIndex(String fileName, int fromIndex) {
        int point = fileName.indexOf("/", fromIndex);
        if (point == -1) {
            point = fileName.indexOf("", fromIndex);
        }
        return point;
    }

    /**
     * 将文件名中的类型部分去掉。
     *
     * @param filename
     * @return
     */
    public static String removeFileExt(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            return filename.substring(0, index);
        } else {
            return filename;
        }
    }

    /**
     * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
     *
     * @param pathName
     * @param fileName
     * @return
     */
    public static String getSubpath(String pathName, String fileName) {
        int index = fileName.indexOf(pathName);
        if (index != -1) {
            return fileName.substring(index + pathName.length() + 1);
        } else {
            return fileName;
        }
    }

    /**
     * 删除一个文件。
     *
     * @param filename
     * @throws IOException
     */
    public static void deleteFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.isDirectory()) {
            throw new IOException("IOException -> BadInputException: not a file.");
        }
        if (!file.exists()) {
            throw new IOException("IOException -> BadInputException: file is not exist.");
        }
        if (!file.delete()) {
            throw new IOException("Cannot delete file. filename = " + filename);
        }
    }

    /**
     * 删除文件夹及其下面的子文件夹
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile())
            throw new IOException("IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }
        dir.delete();
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }

    /**
     * @复制文件，支持把源文件内容追加到目标文件末尾
     * @param src
     * @param dst
     * @param append
     * @throws Exception
     */
    public static void copy(File src, File dst, boolean append) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst, append), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }

    /**
     * 根据URL将文件下载到本地
     * @param urlsrc
     * @param outpath
     * @return
     * @throws Exception
     */
    public static String DownLoadPages(String urlsrc, String outpath) throws Exception
    {
        // 输入流
        InputStream in = null;
        // 文件输出流
        FileOutputStream out = null;

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,5000); //设置连接超时为5秒
            HttpClient client = new DefaultHttpClient(httpParams); // 生成一个http客户端发送请求对象
            HttpGet httpget1 = new HttpGet(urlsrc); //对查询页面get
            HttpResponse httpResponse1 = client.execute(httpget1); // 发送请求并等待响应
            // 判断网络连接是否成功
            System.out.println("状态码："+httpResponse1.getStatusLine().getStatusCode());
            if (httpResponse1.getStatusLine().getStatusCode() != 200)
                System.out.println("网络错误异常！!!!");
            else
                System.out.println("网络连接成功!!!");
            httpget1.abort(); //关闭get
            HttpGet httpget2 = new HttpGet(urlsrc); //对下载链接get实现下载
            HttpResponse httpResponse2 = client.execute(httpget2);
            HttpEntity entity = httpResponse2.getEntity(); // 获取响应里面的内容
            in = entity.getContent(); // 得到服务气端发回的响应的内容（都在一个流里面）
            out = new FileOutputStream(new File(outpath));
            byte[] b = new byte[1024];
            int len = 0;
            while((len=in.read(b))!= -1){
                out.write(b,0,len);
            }
            in.close();
            out.close();
            return outpath;
    }


    /**
     * 将文件上传到阿里云OSS
     * @param AccessKey
     * @param AccessKeySecret
     * @param fileName
     * @param key
     * @return
     */
    public static String uploadToOss(String AccessKey, String AccessKeySecret, String fileName, String key) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = AccessKey;
        String accessKeySecret = AccessKeySecret;
// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
// 上传文件。
        PutObjectResult putObjectResult = ossClient.putObject("zymcht", key, new File(fileName));
        ossClient.shutdown();
        return JSON.toJSONString(putObjectResult);
    }


    public static String getOssUrl(String AccessKey, String AccessKeySecret, String key) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = AccessKey;
        String accessKeySecret = AccessKeySecret;
// 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        URL url = ossClient.generatePresignedUrl("zymcht",key, DateUtil.addDate(new Date(),0,1,0,0,0,0,0), HttpMethod.GET);
        System.out.println("******"+JSON.toJSONString(url)+"******");
// 上传文件。

        ossClient.shutdown();

// 关闭OSSClient。
        return url.toString();
    }


    public static void main(String[] args) {
        try {
            DownLoadPages("http://mmbiz.qpic.cn/mmbiz_jpg/rfnD6pFXHM9jiacpS1hpqRoB8C9ZFmQ6TdOH2nOM3hmW3fIdt6iauHiboJgn3xibgwiatT9iaO9p5h7YtvgolDicuBPTA/0","/Users/martea/Desktop/a.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
