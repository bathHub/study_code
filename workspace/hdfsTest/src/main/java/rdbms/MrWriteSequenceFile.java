package rdbms;

import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class MrWriteSequenceFile {
	public static class MrWriteSequenceFileMap extends Mapper<LongWritable, Text, Text, Text>{
		private String[] info;
		private Text resultKey = new Text();
		private Text resultValue = new Text();
		@Override
		protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			info = value.toString().split("\\t");
			resultKey.set(info[0]+"\t"+info[2]);
			resultValue.set(info[1]);
			context.write(resultKey, resultValue);
		}
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(MrWriteSequenceFile.class);
		job.setJobName("txt to sequence");
		job.setMapperClass(MrWriteSequenceFileMap.class);
		job.setReducerClass(Reducer.class);

		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		Path inPath = new Path("/user-logs-large.txt");
		Path outPath = new Path("/test/rdbms/a");
		outPath.getFileSystem(conf).delete(outPath, true);
		FileInputFormat.addInputPath(job, inPath);
		FileOutputFormat.setOutputPath(job, outPath);

		//不需要reducer节点的执行，只需要reducer的num设置为0
		job.setNumReduceTasks(0);
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
