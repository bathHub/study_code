package rdbms;
//统计用户的名称、ip、login次数、logout次数、newtweet次数、viewuser次数
//结果保存到数据库

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

//把student表的数据从MySQL中读取进来发送到hdfs上
public class UserRead {
	//从数据库中接受数据，以文本方式放到hdfs上
   public static class DBReadMap extends Mapper<LongWritable, StudentDBWritable, Text, NullWritable>{
	   private Text resultKey = new Text();

	@Override
	protected void map(LongWritable key, StudentDBWritable value,
			Mapper<LongWritable, StudentDBWritable, Text, NullWritable>.Context context)
			throws IOException, InterruptedException {
		resultKey.set(value.toString());
		context.write(resultKey, NullWritable.get());
	}
   }
   public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
	Configuration configuration = new Configuration();
	//配置与数据库的链接
	DBConfiguration.configureDB(configuration, "com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/test", "root", "root");
	//DBconfigration.configureDB等同于下面四行
	Job job = Job.getInstance(configuration);
	job.setJarByClass(UserRead.class);
	job.setJobName("read from rdbms");
	
	job.setMapperClass(DBReadMap.class);
	job.setReducerClass(Reducer.class);
	job.setNumReduceTasks(0);
	
	job.setOutputKeyClass(Text.class);
	job.setOutputValueClass(NullWritable.class);
	//设置mr的输入是MySQL数据库
	job.setInputFormatClass(DBInputFormat.class);
	//设置要读取的数据获取方式
	DBInputFormat.setInput(job, StudentDBWritable.class,"SELECT user_id,user_name, user_ip,login_times,logout_times,newtweet_times,viewuser_times FROM user_actions_times", "select count(1) from user_actions_times");
	Path outPath = new Path("/test/rdbms");
	outPath.getFileSystem(configuration).delete(outPath,true);
	FileOutputFormat.setOutputPath(job, outPath);
	System.exit(job.waitForCompletion(true)?0:1);
}
}
