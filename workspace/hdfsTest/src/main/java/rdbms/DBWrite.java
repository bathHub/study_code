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

//将数据从hdfs写出到关系型数据库中
public class DBWrite {
     public static class DBWriteMapper extends Mapper<Object, Text, StudentDBWritable, NullWritable>{
	 private String[] info;
	 private StudentDBWritable resultKey = new StudentDBWritable();
	@Override
	protected void map(Object key, Text value, Mapper<Object, Text, StudentDBWritable, NullWritable>.Context context)
			throws IOException, InterruptedException {
	    info = value.toString().split("\\|");
	    if (info!=null&&info.length==6) {
			resultKey.setStudentId(Integer.valueOf(info[0]));
			resultKey.setAge(Integer.valueOf(info[1]));
			resultKey.setStudentName(info[2]);
			resultKey.setGender(info[3]);
			resultKey.setClassNo(info[4]);
			resultKey.setProId(Integer.valueOf(info[5]));
		}
	    context.write(resultKey, NullWritable.get());
	}
}
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		DBConfiguration.configureDB(configuration, "com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/test","root","root");
		Job job = Job.getInstance(configuration);
		job.setJarByClass(DBWrite.class);
		job.setJobName("write to rdbms");
		
		job.setMapperClass(DBWriteMapper.class);
		job.setReducerClass(Reducer.class);
		job.setNumReduceTasks(0);
		
		//设置输入
		Path inpath = new Path("/test/rdbms/part-m-00000");
		FileInputFormat.addInputPath(job, inpath);
		//设置输出
		job.setOutputFormatClass(DBOutputFormat.class);
		job.setOutputKeyClass(StudentDBWritable.class);
		job.setOutputValueClass(Text.class);
		DBOutputFormat.setOutput(job, "student_from_mr", "student_id, age,student_name,gender,class_no,pro_id");
		
		System.exit(job.waitForCompletion(true)?0:1);
	} 
}
