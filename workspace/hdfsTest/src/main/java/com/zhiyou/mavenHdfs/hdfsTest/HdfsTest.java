package com.zhiyou.mavenHdfs.hdfsTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;

public class HdfsTest {
  private FileSystem fileSystem;
  private Configuration configuration;
  
  public HdfsTest(Configuration configuration) throws IOException{
	  /*configuration.setBoolean("dfs.support.append", true);
		configuration.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
		configuration.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");*/

	  this.configuration = configuration;
	  this.fileSystem = FileSystem.get(configuration);
  }
  
  public HdfsTest(Configuration configuration,String user) throws URISyntaxException, IOException, InterruptedException{
	  this.configuration = configuration;
	  URI uri = new URI("hdfs://master:9000");
	  this.fileSystem = FileSystem.get(uri, configuration,user);
  }
  //新建一个文件夹
  public void mkdir(String path) throws IOException{
	  Path newPath = new Path(path);
	  if (fileSystem.exists(newPath)) {
		System.out.println("创建目录"+path+"已存在");
	}else{
		fileSystem.mkdirs(newPath);
		System.out.println("创建目录"+path+"成功");
	}
	 
  }
  public void close() throws IOException{
	  if (fileSystem!=null) {
		fileSystem.close();
	}
  }
  //创建一个文件并写入内容
  public void  writeFile(String path,String content) throws IOException{
	  Path filePath = new Path(path);
	  FSDataOutputStream outputStream = null;
	  if ( fileSystem.isDirectory(filePath)) {
		System.out.println("这是一个目录无法写文件");
	}else if (fileSystem.isFile(filePath)) {
		System.out.println("是文件可以追加下面的内容");
		 //创建文件并获取文件输出流
		  outputStream = fileSystem.append(filePath);
	
	}else {
		  outputStream = fileSystem.create(filePath);
	
	}
//	  outputStream.writeUTF(content);
	  byte[] str=content.getBytes("GBK");
	  //写入文件
	  outputStream.write(str);
	  //flush文件，将缓冲中的数据全部刷到文件中去	
	  outputStream.hflush();
	  System.out.println("文件创建成功");
  }
  
  
  public void readFile(String path) throws IOException{
	  Path inputPath = new Path(path);
	  //判断路径存在是否存在
	  if (fileSystem.isFile(inputPath)) {
		FSDataInputStream inputStream = fileSystem.open(inputPath);
//		String content = inputStream.readLine();
		InputStreamReader reader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(reader);
		String content = bufferedReader.readLine();
		System.out.println(content);
	}else {
		System.out.println("给定的路径不存在或不是一个文件。");
	}
  }
  //拷贝文件到hdfs
  public void cpToHdfs(String sourcePath,String targetPath) {
	  Path src = new Path(sourcePath);
	  Path dst = new Path(targetPath);
	  try {
		fileSystem.copyFromLocalFile(false, src, dst);
		System.out.println("拷贝成功");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println("拷贝失败");
	}
  }
  //从hdfs上拷贝文件
  public void cpTolocal(String srcPath,String desPath){
	  Path src = new Path(srcPath);
	  Path des = new Path(desPath);
	  try {
//		  第一个false是判断是不是复制完，要删除
		fileSystem.copyToLocalFile(false, src, des,true);
		System.out.println("拷贝成功");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("靠皮失败");
	}
  }
  
  
  //流的方式上传文件(从Windows中上传到Linux中)
  public void uploadFile(String sourcePath ,String targetPath) throws IOException{
//	  Path src = new Path(sourcePath);
	  Path dst = new Path(targetPath);
	  //创建输入流，读取文件
	  FileInputStream fileInputStream = new FileInputStream(sourcePath);
	  //创建输出流，对接hdfs上的文件，并写入输入流中的内容
	  FSDataOutputStream outputStream = fileSystem.create(dst);
	  //从输入流读取，往输出流写入
	  //定义缓冲区
	  byte[] buffer = new byte[2048];
	  
	  int rsize = fileInputStream.read(buffer);
	  while(rsize>=0){
		  outputStream.write(buffer,0,rsize);
		  rsize = fileInputStream.read(buffer);
	  }
	  outputStream.hflush();
	  //关闭链接资源
	  fileInputStream.close();
	  outputStream.close();
  }
  
  
  //文件的下载(从linux中下载到Windows里)
  public void downLoad(String desPath,String srcPath) throws IOException{
	  Path src = new Path(srcPath);
	  //创建输入流，读取文件
	  FSDataInputStream inputStream =fileSystem.open(src);
	  //创建输出流，写进win中
	  FileOutputStream outputStream = new FileOutputStream(desPath);
	  //从输如流里读取，往输出流里写入
	  
	  //定义缓冲区大小
	  byte[] buffer = new byte[2048];
	  int rsize = inputStream.read(buffer);
	  while(rsize>=0){
		  outputStream.write(buffer, 0, rsize);
		  rsize = inputStream.read(buffer);
	  }
	  outputStream.flush();
	  inputStream.close();
	  outputStream.close();
  }
  
  //剪切文件、移动文件、重命名文件
  public void moveFile(String src,String target) throws IOException{
	  Path srcPath = new Path(src);
	  Path desPath = new Path(target);
	  //原路径不存在则终止方法执行
	  if (!fileSystem.exists(srcPath)) {
		System.out.println("源文件路径不存在。。。");
		
		return ;
	}
	  if (fileSystem.exists(desPath)) {
		System.out.println("目标路径已经有文件存在。。。");
		
		return ;
	}
	  fileSystem.rename(srcPath, desPath);
  }
  
  //删除文件
  public void deleteFile(String src) throws IOException{
	  Path srcPath = new Path(src);
	  if (!fileSystem.exists(srcPath)) {
		System.out.println("路径不存在");
	}
	  boolean flag = fileSystem.delete(srcPath,true);
	  if (flag) {
		System.out.println("删除"+srcPath+"成功");
	}else {
		System.out.println("删除"+srcPath+"失败");
	}
  }
  
  //获取文件系统状态
  public void fsStatus() throws IOException{
	  FsStatus fsStatus = fileSystem.getStatus();
	  long capacity = fsStatus.getCapacity();
	  long remaning = fsStatus.getRemaining();
	  long used = fsStatus.getUsed();
	  
	  double c = capacity*1.00/1024/1024/1024;
	  double r = remaning*1.00/1024/1024/1024;
	  double u = used*1.00/1024/1024/1024;
	  DecimalFormat dFormat = new DecimalFormat("##.##");
	  DecimalFormat dFormat2 = new DecimalFormat("#.##%");
	  System.out.println("本文件系统存储空间："+dFormat.format(c)+"G容量；"+dFormat.format(r)+"G("+dFormat2.format(r)+")剩余；"+dFormat.format(u)+"G("+dFormat2.format(u)+")已使用;");
  }
  
  //获取文件信息
  public void listStatus(String path) throws FileNotFoundException, IOException{
	  Path path2 = new Path(path);
	  FileStatus[] fStatus = fileSystem.listStatus(path2);
	  if (fStatus!=null && fStatus.length>0) {
		for(FileStatus fs : fStatus){
			System.out.println(fs.getPath().toString()+":"+fs.getLen()+":"+fs.getPermission()+":"+fs.getOwner()+":"+fs.getGroup());
		}
	}
  }
  
  //修改权限用户demo
  public void testSetFile(String path) throws IOException{
	  Path iPath = new Path(path);
	  fileSystem.setOwner(iPath, "testu1", "testg1");
	  fileSystem.setPermission(iPath, new FsPermission(FsAction.WRITE_EXECUTE,FsAction.READ,FsAction.EXECUTE));
  }
  public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
//	HdfsTest hdfsTest = new HdfsTest(new Configuration());
	  HdfsTest hdfsTest = new HdfsTest(new Configuration(), "root");
	//hdfsTest.mkdir("/javaApiTest");
	
//	hdfsTest.writeFile("/javaApiTest/test1.txt", "中文的哦 但是警方立刻就算了");
//	hdfsTest.readFile("/javaApiTest/test1.txt");
//	hdfsTest.cpTolocal("/javaApiTest/test.txt","c:\\users\\Administrator\\Desktop\\");
	hdfsTest.uploadFile("c:\\users\\Administrator\\Desktop\\need.txt", "/javaApiTest/hadoop1.txt");
//	hdfsTest.downLoad("c:\\users\\Administrator\\Desktop\\hadoop1.txt", "/javaApiTest/hadoop1.txt");
//	hdfsTest.moveFile("/javaApiTest/hadoop1.txt", "/javaApiTest/hadoop2.txt");
//	hdfsTest.deleteFile("/javaApiTest/hadoop2.txt");
//	hdfsTest.fsStatus();
//	hdfsTest.listStatus("/javaApiTest/");
//	hdfsTest.testSetFile("/javaApiTest/test1.txt");
	
	hdfsTest.close();
	 
}
}