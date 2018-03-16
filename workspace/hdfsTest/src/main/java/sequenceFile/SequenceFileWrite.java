package sequenceFile;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.Text;

public class SequenceFileWrite {
     public static final Configuration CONF=new Configuration();
     
     public void writeSequence(String path) throws IOException{
    	 //创建Option对象
    	 SequenceFile.Writer.Option pathOption = SequenceFile.Writer.file(new Path(path));
    	 SequenceFile.Writer.Option kclassOption = SequenceFile.Writer.keyClass(Text.class);
    	 SequenceFile.Writer.Option vclassOption = SequenceFile.Writer.valueClass(IntWritable.class);
    	 SequenceFile.Writer.Option compOption = SequenceFile.Writer.compression(CompressionType.NONE);
    	 //创建sequencefile的writer对象
    	 SequenceFile.Writer sWriter = SequenceFile.createWriter(CONF, pathOption,kclassOption,vclassOption,compOption);
    	 
    	 for(int i=0;i<100;i++){
    		 sWriter.append(new Text("key"+i), new IntWritable(i));
    	 }
    	 sWriter.hsync();
    	 sWriter.hflush();
    	 sWriter.close();
     }
     public static void main(String[] args) throws IOException {
		SequenceFileWrite sequenceFileWrite = new SequenceFileWrite();
		sequenceFileWrite.writeSequence("/test/sequenceFile/");
	}
}
