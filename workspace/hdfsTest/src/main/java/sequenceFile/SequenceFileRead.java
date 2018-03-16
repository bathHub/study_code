package sequenceFile;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class SequenceFileRead {
   public static  final Configuration CONF = new Configuration();
   
   public void readSequenceFile(String path) throws IOException{
	   //构建option来设置读取sequence文件的参数设置
	   SequenceFile.Reader.Option fileopt = SequenceFile.Reader.file(new Path(path));
	   //构建reader
	   SequenceFile.Reader reader = new SequenceFile.Reader(CONF, fileopt);
	   
	   Text key = new Text();
	   IntWritable value = new IntWritable();
	   
	   while (reader.next(key,value)) {
		System.out.println("key:"+key.toString()+",value:"+value.get());
	}
   }
   public static void main(String[] args) throws IOException {
	 SequenceFileRead reader = new SequenceFileRead();
	 reader.readSequenceFile("/test/sequenceFile");
}
}
