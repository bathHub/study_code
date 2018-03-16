package join;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


//统计每一个商品的销售数量
//结束值：商品id，商品名称，销售数量，总金额
public class ProductSaleNumMapJoin {
	//map端，先从缓存文件中读取小表数据，按kv放在map对象的HashMap对象中（内存中）
	//map方法处理大表的每一条数据的时候，先看key在HashMap对象中存在不，不存在就不用输出，存在的话就把关联结果输出
	//输出要考虑聚合值
    public static class ProductSaleNumMapJoinMap extends Mapper<Object, Text, Text, Text>{
    	private Map<String,String> littleTable = new HashMap<String, String>();
    	private Text resultKey = new Text();
    	private Text resultValue = new Text();
    	private String[] info;
    	private String[] littleInfo;
		//读取缓存文件，把数据写入littleTable，key=关联字段，value=其他字段
    	@Override
		protected void setup(Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			//获取缓存文件路径
    		URI[] cacheFileUris =  context.getCacheFiles();
    		FileSystem fileSystem = FileSystem.get(context.getConfiguration());
    		String[] lineInfo=null;
    		for (URI uri : cacheFileUris) {
				FSDataInputStream fsis = fileSystem.open(new Path(uri.getPath()));
				InputStreamReader iReader = new InputStreamReader(fsis,"UTF-8");
				BufferedReader bReader = new BufferedReader(iReader);
				String line = bReader.readLine();
				while (line!=null) {
					lineInfo = line.split("\\|");
					if (lineInfo.length==6) {
                        littleTable.put(lineInfo[0], lineInfo[1]+"|"+lineInfo[2]+"|"+lineInfo[3]+"|"+lineInfo[4]+"|"+lineInfo[5]);						
					}
					line = bReader.readLine();
				}
			}
		}

		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			info = value.toString().split("\\|");
			if (info!=null&&info.length==6) {
				//判断小表里面是否包含和该记录中关联的key，有的话关联成功，否则关联不成功
				if (littleTable.containsKey(info[2])) {
					//小表数据（字符串）
					littleInfo = littleTable.get(info[2]).split("\\|");
					//要聚合每个商品的销售数量，key=商品id+商品名称作为，value=商品数量+商品销售金额
					resultKey.set(info[2]+"|"+littleInfo[1]);
					resultValue.set(info[3]+"|"+info[4]);
					
					context.write(resultKey, resultValue);
				}
			}
		}
		
    	
    }
    //聚合map输出key的值，包括两部分，销售数量和销售金额
    public static class ProductSaleNumMapJoinReduce extends Reducer<Text, Text, Text, Text>{
    	private String[] info;
    	private Text resultValue = new Text();
    	private double moneySum;
    	private int quatitySum;
		@Override
		protected void reduce(Text key, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
		moneySum = 0;
		quatitySum = 0;
		for (Text value : values) {
			info = value.toString().split("\\|");
			if (info.length == 2) {
				moneySum+=Double.valueOf(info[1]);
				quatitySum+=Integer.valueOf(info[0]);
			}
		}
		resultValue.set(quatitySum+"|"+moneySum);
		context.write(key, resultValue);
		}
    	
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(ProductSaleNumMapJoin.class);
		job.setJobName("map join");
		
		job.setMapperClass(ProductSaleNumMapJoinMap.class);
		job.setReducerClass(ProductSaleNumMapJoinReduce.class);
		job.setCombinerClass(ProductSaleNumMapJoinReduce.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//添加分布式缓存文件
		job.addCacheFile(new Path("/text/products/part-r-00000-3228120c-1b56-4bd5-8ba2-9a0081c601ff.txt").toUri());
		job.addCacheFile(new Path("/text/products/part-r-00001-3228120c-1b56-4bd5-8ba2-9a0081c601ff.txt").toUri());
		
		Path input = new Path("/text/order_items");
		Path output = new Path("/des/mapjoin");
		output.getFileSystem(configuration).delete(output,true);
		FileInputFormat.addInputPath(job, input);
		FileOutputFormat.setOutputPath(job, output);
		
		System.exit(job.waitForCompletion(true)?0:1);
	}
}
