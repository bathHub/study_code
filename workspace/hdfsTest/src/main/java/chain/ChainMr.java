package chain;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.chain.ChainMapper;
import org.apache.hadoop.mapreduce.lib.chain.ChainReducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


//1.单价大于150
//2.数量大于2
//3.状态是COMPLETE
//4.总销量大于100
//统计满足条件的商品的id、销量
public class ChainMr {
    public static class ReadFileMap extends Mapper<Object, Text, Text, Text>{
    	private Text resultValue = new Text();
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			context.write(value, resultValue);
		}
    	
    }
    public static class ChainMrPriceMap extends Mapper<Text, Text, Text, Text>{
    	 private double priceCondition = 150;
    	 private String[] info;
		@Override
		protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			info = key.toString().split("\\|");
			if (info.length==9) {
				if (Double.valueOf(info[5])>priceCondition) {
					context.write(key, value);
				}
			}
		}
    	 
    }
    public static class ChainMrQuantityMap extends Mapper<Text, Text, Text, Text>{
   	 private int quantity = 1;
   	 private String[] info;
		@Override
		protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			info = key.toString().split("\\|");
			if (info.length==9) {
				if (Double.valueOf(info[3])>quantity) {
					context.write(key, value);
				}
			}
		}
   	 
   }
    public static class ChainMrStatusMap extends Mapper<Text, Text, Text, Text>{
      	 private String status = "COMPLETE";
      	 private String[] info;
   		@Override
   		protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
   				throws IOException, InterruptedException {
   			info = key.toString().split("\\|");
   			if (info.length==9) {
   				if (status.equals(info[8])) {
   					context.write(key, value);
   				}
   			}
   		}
      	 
      }
    public static class ChainMrForReduceMap extends Mapper<Text, Text, Text, Text>{
    	private Text resultKey = new Text();
    	private Text resultValue = new Text();
    	private String[] info;
		@Override
		protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			info = key.toString().split("\\|");
			resultKey.set(info[2]);
			resultValue.set(info[3]);
			context.write(resultKey, resultValue);
		}
    	
    }
    public static class ChainMrReduce extends Reducer<Text, Text, Text, Text>{
    	private Text resultValue = new Text();
    	private int sum;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			sum = 0;
			for(Text value:values){
				sum +=Integer.valueOf(value.toString());
			}
			resultValue.set(sum+"");
			context.write(key, resultValue);
		}
    	
    }
    public static class ChainMrSumQuatityMap extends Mapper<Text, Text, Text, Text>{
        private int quantity = 100;
		@Override
		protected void map(Text key, Text value, Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			if (Integer.valueOf(value.toString())>100) {
				context.write(key, value);
			}
		}
    	
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job =Job.getInstance(configuration);
		job.setJarByClass(ChainMr.class);
		job.setJobName("chain mr");
		
		ChainMapper.addMapper(job, ReadFileMap.class, Object.class, Text.class, Text.class, Text.class, configuration);
		ChainMapper.addMapper(job, ChainMrPriceMap.class, Text.class, Text.class, Text.class, Text.class, configuration);
		ChainMapper.addMapper(job, ChainMrQuantityMap.class, Text.class, Text.class, Text.class, Text.class, configuration);
		ChainMapper.addMapper(job, ChainMrStatusMap.class, Text.class, Text.class, Text.class, Text.class, configuration);
		ChainMapper.addMapper(job, ChainMrForReduceMap.class, Text.class, Text.class, Text.class, Text.class, configuration);
		
		ChainReducer.setReducer(job, ChainMrReduce.class, Text.class, Text.class, Text.class, Text.class, configuration);
		ChainReducer.addMapper(job, ChainMrSumQuatityMap.class, Text.class, Text.class, Text.class, Text.class, configuration);
		
		FileInputFormat.addInputPath(job, new Path("/des/reducejoin"));
		Path output = new Path("/des/reducejoinresult");
		output.getFileSystem(configuration).delete(output,true);
		
		FileOutputFormat.setOutputPath(job, output);
		
		System.exit(job.waitForCompletion(true)?0:1);
		
	}
}
