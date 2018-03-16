package rdbms;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
public class UserWrite {
	

	

	//将数据从hdfs写出到关系型数据库中

	     public static class DBWriteMapper extends Mapper<Object, Text, UserDBWritable, NullWritable>{
		 private String[] info;
		 private UserDBWritable resultKey = new UserDBWritable();
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, UserDBWritable, NullWritable>.Context context)
				throws IOException, InterruptedException {
		    info = value.toString().split("\\|");
		    if (info!=null&&info.length==6) {
				resultKey.setUserId(Integer.valueOf(info[0]));
				resultKey.setUserName(info[1]);
				resultKey.setUserIp(info[2]);
				resultKey.setLoginTimes(info[3]);
				resultKey.setLogoutTimes(info[4]);
				resultKey.setNewtweetTimes(info[5]);
				resultKey.setViewuserTimes(info[6]);
			}
		    context.write(resultKey, NullWritable.get());
		}
	}
	    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
			Configuration configuration = new Configuration();
			DBConfiguration.configureDB(configuration, "com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/test","root","root");
			Job job = Job.getInstance(configuration);
			job.setJarByClass(UserWrite.class);
			job.setJobName("write to rdbms");
			
			job.setMapperClass(DBWriteMapper.class);
			job.setReducerClass(Reducer.class);
			job.setNumReduceTasks(0);
			
			//设置输入
			Path inpath = new Path("/test/rdbms/part-m-00000");
			FileInputFormat.addInputPath(job, inpath);
			//设置输出
			job.setOutputFormatClass(DBOutputFormat.class);
			job.setOutputKeyClass(UserDBWritable.class);
			job.setOutputValueClass(Text.class);
			DBOutputFormat.setOutput(job, "user_from_mr", "user_id`, `user_name`,`user_ip`,`login_times`,`logout_times`,`newtweet_times`,`viewuser_times`");
			
			System.exit(job.waitForCompletion(true)?0:1);
		} 
	

}
