package sort;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MrReadSequenceFile {
      public static class MrReadSequenceFileMap extends Mapper<Text, Text, Text, IntWritable>{
private IntWritable resultValue = new IntWritable(1);
		@Override
		protected void map(Text key, Text value, Mapper<Text, Text, Text, IntWritable>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			if (value!=null && value.toString().equals("login")){
				context.write(key, resultValue);
				
			}
		}
		
    	
      }
     
      public static class MrReadSequenceFileReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
  private int sum;
  private IntWritable resultValue = new IntWritable(0);
  		@Override
  		protected void reduce(Text key, Iterable<IntWritable> values,
  				Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
  			// TODO Auto-generated method stub
  			sum = 0;
  			for (IntWritable  value : values) {
  				sum+=value.get();
  			}
  			resultValue.set(sum);
  			context.write(key, resultValue);
  		}
  		//聚合计算计数过程
  		
  	}
      public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(MrReadSequenceFile.class);
		job.setJobName("read sequence file");
		
		job.setMapperClass(MrReadSequenceFileMap.class);
		job.setReducerClass(MrReadSequenceFileReducer.class);
		
		job.setCombinerClass(MrReadSequenceFileReducer.class);
		
		//设置输入文件格式为sequencefileinputformat
		job.setInputFormatClass(SequenceFileInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		Path inpPath = new Path("/test/part-m-00000");
	    Path outPath = new Path("/test/m");
	  outPath.getFileSystem(configuration).delete(outPath,true);
	  FileInputFormat.addInputPath(job, inpPath);
	  FileOutputFormat.setOutputPath(job, outPath);
	  
	  System.exit(job.waitForCompletion(true)?0:1);
	  
		
	}
}
