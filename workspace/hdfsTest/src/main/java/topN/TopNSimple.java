package topN;

import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class TopNSimple {
	
    public static class TopNSimpleReduce extends Reducer<Text, Text, Text, Text>{
    	private Text resultKey = new Text();
    	private Text resultValue = new Text();
        private TreeMap<Integer, String> topNContainer = new TreeMap<Integer, String>();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
		   if (topNContainer.size()>=5) {
			topNContainer.put(Integer.valueOf(key.toString().trim()), "");
			topNContainer.remove(topNContainer.firstKey());
		}else {
			topNContainer.put(Integer.valueOf(key.toString().trim()), "");
		}
		}
		@Override
		protected void cleanup(Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			if (!topNContainer.isEmpty()) {
				Set<Integer> keys = topNContainer.descendingKeySet();
				for (Integer key : keys) {
					resultKey.set(key+"");
					context.write(resultKey, resultValue);
				}
			}
			
			/*Set<Integer> keys = topNContainer.keySet();
			for (Integer key : keys) {
				resultKey.set(key.toString());
				context.write(resultKey, resultValue);
			}*/
		}
		
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(TopNSimple.class);
		job.setJobName("topn");
		
		job.setMapperClass(Mapper.class);
		job.setReducerClass(TopNSimpleReduce.class);
		job.setCombinerClass(TopNSimpleReduce.class);
		
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.addInputPath(job, new Path("/topN/fortopn1.txt"));
		Path outPath = new Path("/test/topN");
		FileOutputFormat.setOutputPath(job, outPath);
		outPath.getFileSystem(configuration).delete(outPath,true);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
}
