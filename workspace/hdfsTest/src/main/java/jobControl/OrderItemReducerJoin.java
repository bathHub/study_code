package jobControl;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import sort.RepeatSort2.textPair;
//关联order和order_item
public class OrderItemReducerJoin {
	public static class FlagedValue implements Writable{
    private String value;
    private String tableName;
		public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void write(DataOutput out) throws IOException {
			out.writeUTF(value);
			out.writeUTF(tableName);
	}
	public void readFields(DataInput in) throws IOException {
            value = in.readUTF();
            tableName = in.readUTF();
	}
	}
   //map处理两张表的数据，并给每个数据打个标识，标识其来源
	public static class OrderItemReducerJoinMap extends Mapper<Object, Text, Text, FlagedValue>{
		private String tableName;
		private String[] info;
		private Text resultKey = new Text();
		private FlagedValue resultValue = new FlagedValue();
		
		
		@Override
		protected void setup(Mapper<Object, Text, Text, FlagedValue>.Context context)
				throws IOException, InterruptedException {
			FileSplit fileSplit = (FileSplit) context.getInputSplit();
		    String location = fileSplit.getPath().toString();
		
				if (location.contains("orders")) {
					tableName = "orders";
				}else if (location.contains("order_items")) {
					tableName = "order_items";
				}
			
		}


		//处理两张表的数据，输出key=关联字段，输出value=各自数据+tableName
		@Override
		protected void map(Object key, Text value, Mapper<Object, Text, Text, FlagedValue>.Context context)
				throws IOException, InterruptedException {
			info = value.toString().split("\\|");
			resultValue.setTableName(tableName);
			if (tableName.equals("orders")) {
				//对order的解析过程
				if(info.length==4){
					resultKey.set(info[0]);
					//订单日期|客户id|订单状态
					resultValue.setValue(info[1]+"|"+info[2]+"|"+info[3]);
				}
			}else if (tableName.equals("order_items")) {
				//对order_items的解析过程
				if (info.length==6) {
					resultKey.set(info[1]);
					//订单明细id|产品id|数量|总金额|单价
					resultValue.setValue(info[0]+"|"+info[2]+"|"+info[3]+"|"+info[4]+"|"+info[5]);
				}
			}
			context.write(resultKey, resultValue);
		}
	}
	public static class OrderItemReducerJoinReduce extends Reducer<Text, FlagedValue, Text, NullWritable>{
		private List<String> orderList = new ArrayList<String>();
		private List<String> itemsList = new ArrayList<String>();
		private Text resultKey = new Text();
		@Override
		protected void reduce(Text key, Iterable<FlagedValue> values,
				Reducer<Text, FlagedValue, Text, NullWritable>.Context context) throws IOException, InterruptedException {
			orderList.clear();
			itemsList.clear();
			//先把values的值分成两组，放入orderList和itemsList中
			for (FlagedValue value : values) {
				if (value.getTableName().equals("orders")) {
					orderList.add(value.value);
				}else if (value.getTableName().equals("order_items")) {
					itemsList.add(value.getValue());
				}
			}
			//然后把两个数据笛卡尔乘积
			for(String order:orderList){
				for(String item:itemsList){
					//order_id|订单明细id|产品id|数量|总金额|单价订单日期|客户id|订单状态
					resultKey.set(key.toString()+"|"+item+"|"+order);
					context.write(resultKey, NullWritable.get());
				}
			}
		}
		
	}
	public static Job OrderItemReducerJoinJob() throws Exception{
		Configuration configuration = new Configuration();
		Job job = Job.getInstance(configuration);
		job.setJarByClass(OrderItemReducerJoin.class);
		job.setJobName("reducer join");
		
		job.setMapperClass(OrderItemReducerJoinMap.class);
		job.setReducerClass(OrderItemReducerJoinReduce.class);
		job.setNumReduceTasks(2);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(FlagedValue.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(NullWritable.class);
		
		Path input1 = new Path("/text/orders");
		Path input2 = new Path("/text/order_items");
		Path output = new Path("/des/reducejoin");
		output.getFileSystem(configuration).delete(output,true);
		FileInputFormat.addInputPath(job, input1);
		FileInputFormat.addInputPath(job, input2);
		FileOutputFormat.setOutputPath(job, output);
		
		return job;
	}
}
