package index;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class ReverseIndex {
	//因为在map端就统计单词在文件中出现的次数，因此，一个文件只能经由一个map任务处理
	//否则数据就会出错，使用自定义的InputFormat，通过重写isSplitable方法，让一个文件
	//不管它有多少个block只能由一个map节点处理，这样就能避免上述问题
	//另：当处理一些不可分片压缩格式的文件的时候也需要一个文件不能被划分到多个split中去（文件不能被分片）
	public static class ReverseIndexInputFormat extends TextInputFormat{

		@Override
		protected boolean isSplitable(JobContext context, Path file) {
			return false;
		}
		
	} 
	//获取每一个单词的文件地址，并计算每个单词在每个文件中出现的词频做准备
	//key=单词  ，value=文件地址
	public static class ReverseIndexMap extends Mapper<Object, Text, Text, Text>{
		private Text resultKey = new Text();
		private Text resultValue = new Text();
		private FileSplit fileSplit;
		private String path;
		private String[] info;
		
		
		@Override
		protected void setup(Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			fileSplit = (FileSplit) context.getInputSplit();
            path = fileSplit.getPath().toUri().toString();
		}


		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			
            info = value.toString().split("[\\s\\.,]");
            for(String word:info){
            	resultKey.set(word);
            	resultValue.set(path);
            	context.write(resultKey, resultValue);
            }
		}

	}
	//在map节点计算每个单词在每个文件中出现的词频
	//输出：key=单词+\t+文件地址，value=不重要
	//输出：key=单词，value=文件地址（词频）
	public static class ReverseIndexCombiner extends Reducer<Text, Text, Text, Text>{
		private Text resultValue = new Text();
		private int sum;
		private String valuestr;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
		 
		  sum=0;
		  for (Text value : values) {
			sum+=1;
			valuestr= value.toString();
		}
		  
		  resultValue.set(valuestr+"["+sum+"]");
		  context.write(key, resultValue);
		}
		
	}
	//将每个单词进行聚合，把合并后的（文件地址【词频】）串接起来
	//输入：key=单词，value=文件地址（词频）
	//输出：key=单词，value=文件地址1（词频）--》文件地址3（词频）--》。。
	public static class ReverseIndexReducer extends Reducer<Text, Text, Text, Text>{
		private Text resultValue = new Text();
        private StringBuffer buffer = new StringBuffer();
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			buffer = new StringBuffer();
			for (Text value : values) {
				if (buffer.length()==0) {
					buffer.append(value.toString());
					
				}else {
					buffer.append("-->");
					buffer.append(value);
				}
			}
			resultValue.set(buffer.toString());
			context.write(key, resultValue);
		}
		
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(ReverseIndex.class);
		job.setJobName("reverse index");
		job.setMapperClass(ReverseIndexMap.class); 
		job.setCombinerClass(ReverseIndexCombiner.class);
		job.setReducerClass(ReverseIndexReducer.class);	
		job.setInputFormatClass(ReverseIndexInputFormat.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileInputFormat.addInputPath(job, new Path("/indexDirectory"));
		Path outPath = new Path("/test/index");
		outPath.getFileSystem(configuration).delete(outPath,true);
		FileOutputFormat.setOutputPath(job, outPath);
		
		
		job.setNumReduceTasks(2);
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
