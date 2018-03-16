package sort;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.filecache.DistributedCache;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.partition.InputSampler;
import org.apache.hadoop.mapreduce.lib.partition.TotalOrderPartitioner;

public class TotalSort {
   public static class TotalSortMap extends Mapper<Object, Text, Text, NullWritable>{

	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
    context.write(value, NullWritable.get());
	}
	   
   }
   public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
	  //1.定义抽样
	   Configuration conf = new Configuration();
	   InputSampler.RandomSampler<Text, NullWritable> sampler = new InputSampler.RandomSampler<Text, NullWritable>(0.2,20);
	   
	   //2.设置分区文件
	   FileSystem fileSystem = FileSystem.get(conf);
	   Path partitionFile = new Path("_partitions");
	   TotalOrderPartitioner.setPartitionFile(conf, partitionFile);
	   //3.设置job
	   Job job = Job.getInstance(conf);
	   job.setJarByClass(TotalSort.class);
	   job.setJobName("total order word");
	   
	   //加入分布式缓存文件
	   job.addCacheFile(partitionFile.toUri());
	   job.setMapperClass(Mapper.class);
	   job.setReducerClass(Reducer.class);
	   
	   job.setOutputKeyClass(Text.class);
	   job.setOutputValueClass(Text.class);
	   
	   //设置分区器
	   job.setPartitionerClass(TotalOrderPartitioner.class);
	   //设置reducer节点个数
	   job.setNumReduceTasks(2);
	   //设置输入文件的格式，默认的是fileinputFormat,输出key是LongWritable，value时间text
	   //keyValueTextInputFormat是读取文本文件，输出的key是text，value也是text，
//	   key是读取某一行从开头到第一个\t字符
//	   value是第一个\t字符后面开始到本行结束
	   job.setInputFormatClass(KeyValueTextInputFormat.class);
	   
	   Path input = new Path("/ts.txt");
	   Path output = new Path("/test");
	   fileSystem.delete(output,true);
	   FileInputFormat.addInputPath(job, input);
	   FileOutputFormat.setOutputPath(job, output);
	   
	   //将随机抽样写进分区文件
	   InputSampler.writePartitionFile(job, sampler);
	   
       URI  uri = new URI("hdfs://master:9000/userAdministrator/_partitions");	
       Path path = new Path("_partitions");
       DistributedCache.addCacheFile(uri, conf);
	   System.exit(job.waitForCompletion(true)?0:1);
	   
}
}
