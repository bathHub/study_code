package jobControl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;




public class OrderStateSum {
    public static class OrderStateSumMap extends Mapper<Object, Text, Text, Text>{
    	private Text resultKey = new Text();
    	private Text resultValue = new Text();
    	private String[] info;
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
		   info = value.toString().split("\\|");
		   if (info.length==9) {
			resultKey.set(info[8]);
			resultValue.set(info[4]);
			context.write(resultKey, resultValue);
		}
		}
    	
    	
    }
    public static class OrderStateSumReduce extends Reducer<Text, Text, Text, Text>{
    	private Text resultValue = new Text();
    	private double sum;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
		sum=0;
		for (Text value : values) {
			sum+=Double.valueOf(value.toString());
		}
		resultValue.set(String.valueOf(sum));
		context.write(key, resultValue);
		}
    	
    }
    public static Job orderStateSumJob() throws Exception{
    	Configuration configuration = new Configuration();
    	Job job = Job.getInstance(configuration);
    	job.setJarByClass(OrderStateSum.class);
    	job.setJobName("order sum");
    	
    	job.setMapperClass(OrderStateSumMap.class);
    	job.setReducerClass(OrderStateSumReduce.class);
    	
    	/*job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlagedValue.class);*/
    	job.setOutputKeyClass(Text.class);
    	job.setOutputValueClass(Text.class);
    	
    	Path  input = new Path("/des/reducejoin");
    	Path output = new Path("/des/reduce");
    	output.getFileSystem(configuration).delete(output,true);
    	
    	FileOutputFormat.setOutputPath(job, output);
    	FileInputFormat.addInputPath(job, input);
    	
    	return job;
    }
}
