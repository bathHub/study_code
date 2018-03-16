package sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import sort.RepeatSort.TextPairPartitioner.TextPairReduceComparetor;

//使用两个字段来排序
public class RepeatSort {
      //定义类来封装两个字段，实现排序
	public static class TextPair implements WritableComparable<TextPair>{
		private String name;
		private String score;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}
//序列化
		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub
		  out.writeUTF(name);
		  out.writeUTF(score);
		}
//反序列化
		public void readFields(DataInput in) throws IOException {
			// TODO Auto-generated method stub
			this.name = in.readUTF();
			this.score = in.readUTF();
		}
//排序规则
		public int compareTo(TextPair o) {
			// TODO Auto-generated method stub
		   if (this.name.equals(o.getName())) {
			return this.score.compareTo(o.getScore());
		}	else {
			return this.name.compareTo(o.getName());
		}
			
		}
		
	}
	//自定义分区规则
	public static class TextPairPartitioner extends Partitioner<TextPair, Text>{

		@Override
		public int getPartition(TextPair key, Text value, int numPartitions) {
       int partitionNo = (key.getName().hashCode()&Integer.MAX_VALUE)% numPartitions;
			return partitionNo;
		}
		//自定义writableCompator。用来控制不同的key可以调用同一个reduce方法
		public static class TextPairReduceComparetor extends WritableComparator{
  //调用父类构造方法设置参数
			 public TextPairReduceComparetor(){
				super(TextPair.class,true);
			}
			@Override
			public int compare(WritableComparable a, WritableComparable b) {
	            TextPair ta = (TextPair)a;
	            TextPair tb = (TextPair)b;
				return ta.getName().compareTo(tb.getName());
			}
			
		}
		
	}
	public static class repeatSortMap extends Mapper<Object, Text , TextPair, Text>{
		private TextPair resultKey = new TextPair();
		private Text resultValue = new Text();
		private String[] info;
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, TextPair, Text>.Context context)
				throws IOException, InterruptedException {
			info = value.toString().split("\\t");
			if (info!=null&&info.length==3) {
				resultKey.setName(info[0]);
				resultKey.setScore(info[2]);
				
		     resultValue.set(info[1]);
		     context.write(resultKey, resultValue);
			}
		}
		
	}
	public static class repeatSortReducer extends Reducer<TextPair, Text, Text, NullWritable>{
		private Text resultKey = new Text();

		@Override
		protected void reduce(TextPair key, Iterable<Text> values,
				Reducer<TextPair, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
			String score = "";
        for (Text value : values) {
			  resultKey.set(key.getName()+"\t"+value.toString()+"\t"+key.getScore());
			  if (score.equals("")) {
				score+=key.getScore();
			}else {
				score+=","+key.getScore();
			}
			  context.write(resultKey, NullWritable.get());
		}
        //测试分组效果
        
        resultKey.set("汇总:"+score);
       
        context.write(resultKey, NullWritable.get());
		}
		
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(RepeatSort.class);
		job.setJobName("repeat sort");
		
		job.setMapperClass(repeatSortMap.class);
		job.setReducerClass(repeatSortReducer.class);
		
		//使用自定义的分区
		job.setPartitionerClass(TextPairPartitioner.class);
		
		//设置key被分组调用reduce方法的比较器
		job.setGroupingComparatorClass(TextPairReduceComparetor.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		job.setMapOutputKeyClass(TextPair.class);
		job.setMapOutputValueClass(Text.class);
		
		Path inPath = new Path("/ss2.txt");
	    Path outPath = new Path("/test/repeatSort/test2");
	    outPath.getFileSystem(configuration).delete(outPath,true);
	    
	    FileInputFormat.addInputPath(job, inPath);
	    FileOutputFormat.setOutputPath(job, outPath);
	    
	    job.setNumReduceTasks(2);
	    
	    System.exit(job.waitForCompletion(true)?0:1);
	}
}
