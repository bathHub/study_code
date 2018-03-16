package sort;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class RepeatSort2 {
     public static class textPair implements WritableComparable<textPair>{
      private String name;
      private Integer score;
      
    	 
		

		public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub
			out.writeUTF(name);
			out.writeInt(score);
		}

		public void readFields(DataInput in) throws IOException {
			// TODO Auto-generated method stub
			this.name = in.readUTF();
			this.score = in.readInt();
		}

		public int compareTo(textPair o) {
			if (this.name.equals(o.getName())) {
		return this.score-o.getScore();
			}else {
				return this.name.compareTo(o.getName());
			}
		}

		
    	 
     }
     //自定哟分区规则
     public static class textPairPartition extends Partitioner<textPair, Text>{

		@Override
		public int getPartition(textPair key, Text value, int numPartitions) {
			int partitionNo = (key.getName().hashCode() & Integer.MAX_VALUE)%numPartitions;
			return partitionNo;
		}
    	 
     }
     
     public static class repeatSortMap extends Mapper<Object, Text, textPair, Text>{
    	 private textPair resultKey = new textPair();
    	 private Text resultValue = new Text();
    	 private String[] info;

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, textPair, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			info  = value.toString().split("\\s");
			if (info!=null&&info.length==3) {
				resultKey.setName(info[0]);
				resultKey.setScore(Integer.parseInt(info[2]));
				
				resultValue.set( info[1]);
				context.write(resultKey, resultValue);
			}
		}
    	 
     }
     public static class repeatSortReducer extends Reducer<textPair, Text, Text, NullWritable>{
    	 private Text resultKey = new Text();
    	 private Integer sum=0;

		@Override
		protected void reduce(textPair key, Iterable<Text> values,
				Reducer<textPair, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
			// TODO Auto-generated method stub
          for (Text value : values) {
        	  resultKey.set(key.getName()+"\t"+value.toString()+"\t"+key.getScore());
        	  sum+=key.getScore();
			  context.write(resultKey, NullWritable.get());
		}
          context.write(resultKey, NullWritable.get());
		}
    	 
     }
     public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(RepeatSort2.class);
		job.setJobName("repeat sort2");
		
		job.setMapperClass(repeatSortMap.class);
		job.setReducerClass(repeatSortReducer.class);
		
		job.setPartitionerClass(textPairPartition.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		job.setMapOutputKeyClass(textPair.class);
		job.setMapOutputValueClass(Text.class);
		
		Path inPath = new Path("/ss1.txt");
	    Path outPath = new Path("/test/repeat2Sort");
	    outPath.getFileSystem(configuration).delete(outPath,true);
	    
	    FileInputFormat.addInputPath(job, inPath);
	    FileOutputFormat.setOutputPath(job, outPath);
	    
	    job.setNumReduceTasks(2);
	    
	    System.exit(job.waitForCompletion(true)?0:1);
	}
}
