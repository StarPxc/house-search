package yuqiao.housesearch.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author 浦希成
 * 2018-12-18
 */
@Slf4j
public class FileUtil {
    /**
     * 读取文件内容
     */
    public static String  readTxtFile(String filePath){
        try {
            String encoding="UTF-8";
            StringBuilder result=new StringBuilder();
            File file=new File(filePath);
            if(file.isFile() && file.exists()){
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while((lineTxt = bufferedReader.readLine()) != null){
                    result.append(lineTxt);
                }
                read.close();
                return result.toString();
            }else{
                log.error("找不到指定的文件");
            }
        } catch (Exception e) {
            log.error("读取文件内容出错 {}",e);
        }
    return "";
    }

    /**
     * 保存文件
     * @param is
     * @param fileName
     * @throws IOException
     */
    public static void saveFile(InputStream is,String fileName) throws IOException {
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        out=new BufferedOutputStream(new FileOutputStream(fileName));
        int len=-1;
        byte[] b=new byte[1024];
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }

}
