package rdbms;
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
//统计用户的ID、名称、ip、login次数、logout次数、newtweet次数、viewuser次数
//结果保存到数据库
public class User_dealwith {	
	private Integer userId;
	private String userName;
	private String userIp;
	private String loginTimes;
	private String logoutTimes;
	private String newtweetTimes;
	private String viewuserTimes;
	
		public static class repeatSortMap extends Mapper<Object, Text , Text, Text>{
			private Text resultKey = new Text();
			private Text resultValue = new Text();
			private String[] info;
			@Override
			protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
					throws IOException, InterruptedException {
				info = value.toString().split("\\|");
				if (info!=null&&info.length==3) {
//					resultKey.setName(info[0]);
//					resultKey.setScore(info[2]);
//					
			     resultValue.set(info[1]);
			     context.write(resultKey, resultValue);
				}
			}
			
		}
		public static class repeatSortReducer extends Reducer<Text, Text, Text, NullWritable>{
			private Text resultKey = new Text();

			@Override
			protected void reduce(Text key, Iterable<Text> values,
					Reducer<Text, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
				String score = "";
	        for (Text value : values) {
//				  resultKey.set(key.getName()+"\t"+value.toString()+"\t"+key.getScore());
//				  if (score.equals("")) {
//					score+=key.getScore();
//				}else {
//					score+=","+key.getScore();
//				}
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
			job.setJarByClass(User_dealwith.class);
			job.setJobName("repeat sort");
			
			job.setMapperClass(repeatSortMap.class);
			job.setReducerClass(repeatSortReducer.class);
			
			
			
			//设置key被分组调用reduce方法的比较器
			job.setGroupingComparatorClass(TextPairReduceComparetor.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(NullWritable.class);
			job.setMapOutputKeyClass(Text.class);
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
