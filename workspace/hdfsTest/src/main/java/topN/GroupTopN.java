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
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class GroupTopN {
public static class GroupTopNMap extends Mapper<Object, Text, Text, Text>{
	private Text resultKey = new Text();
	private Text resultValue = new Text();
	private String[] info;
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		info = value.toString().split("\\t");
		resultKey.set(info[0]);
		resultValue.set(info[1]+"\t"+info[2]);
		context.write(resultKey, resultValue);
	}
	
} 
public static class GroupTopNReduce extends Reducer<Text, Text, Text, Text>{
	private TreeMap<Integer, String>  topNContainer;
	private String[] info;
	private Text resultValue = new Text();
	@Override
	protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
	topNContainer = new TreeMap<Integer, String>();
	for (Text value : values) {
		info = value.toString().split("\\t");
		if (topNContainer.size()>=3) {
			if (Integer.valueOf(info[1])>topNContainer.firstKey()) {
				topNContainer.put(Integer.valueOf(info[1]), info[0]);
				topNContainer.remove(topNContainer.firstKey());
			}
		}else {
			topNContainer.put(Integer.valueOf(info[1]), info[0]);
		}
		/*
		if (topNContainer.size()>3) {
			topNContainer.remove(topNContainer.firstKey());
		}*/
		}
		Set<Integer> keys = topNContainer.descendingKeySet();
		for (Integer ikey : keys) {
			resultValue.set(topNContainer.get(ikey)+"\t"+ikey);
			context.write(key, resultValue);
		}
	}
	
	}
	

public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	if(args==null || args.length<2){
		System.err.println("参数不正确：1.输入路径；2.输出路径");
		System.exit(1);
	}
	Configuration configuration = new Configuration();
	Job job = Job.getInstance(configuration);
	job.setJarByClass(GroupTopN.class);
	job.setJobName("group topN");
	
	job.setMapperClass(GroupTopNMap.class);
	job.setReducerClass(GroupTopNReduce.class);
	job.setCombinerClass(GroupTopNReduce.class);
	
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(Text.class);
//	"/topN/sss.txt"
	//   /test/GroupTopN"
	FileInputFormat.addInputPath(job, new Path(args[0]));
	Path outPath = new Path(args[1]);
	outPath.getFileSystem(configuration).delete(outPath,true);
	FileOutputFormat.setOutputPath(job, outPath);
	
	System.exit(job.waitForCompletion(true)?0:1);
}
}
