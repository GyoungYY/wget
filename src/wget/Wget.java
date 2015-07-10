package wget;

//import java.io.BufferedOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Wget {
	public static void main(String[] args) {
		String url="http://echarts.baidu.com/index.html";
		String location="D:/succezIDE/workspace/wget/";
		String location_temp="Download";
		int index = url.lastIndexOf("/");  
		if(index>0){
			location_temp=url.substring(index+1);
		}
		try{
		fileGetContent(url,location+location_temp);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void fileGetContent(String url,String dest)throws IOException{
		URL url1=null;
		HttpURLConnection connection=null;
		try{
			url1=new URL(url);
			connection=(HttpURLConnection)url1.openConnection();  //到URL远程对象的连接
			connection.setConnectTimeout(2*1000);          //设置连接超时
			connection.setReadTimeout(2*1000);
			connection.connect();
			File temp=new File(dest);
			if(!temp.exists()){
				temp.getParentFile().mkdirs();
				temp.createNewFile();
			}
			writeToFile(dest, connection);
		}catch(IOException e){
			System.out.println("error geturl:"+url);
		}finally{

			if(connection!=null){
				connection.disconnect();
			}
		}
	}
	private static void writeToFile(String dest, HttpURLConnection connection) throws FileNotFoundException,
			IOException {
		BufferedOutputStream fos=new BufferedOutputStream(new FileOutputStream(dest));    //输出到文件
		try{
		//BufferedOutputStream os = new BufferedOutputStream(fos);
		InputStream is = connection.getInputStream();
		byte[] buff = new byte[1024];
		int length=0;
		while((length=is.read(buff, 0, 1024))!=-1){
			fos.write(buff,0,length);
		}		
		}
		finally{
			if(fos!=null){
				fos.close();
			}
		}
	}
}
