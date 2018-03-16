package jobControl;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;

public class JobChain {
   public static int OrderStateSumJobChain() throws Exception{
	   Job job1 = OrderItemReducerJoin.OrderItemReducerJoinJob();
	   Job job2 = OrderStateSum.orderStateSumJob();
	   ControlledJob joinJob = new ControlledJob(job1.getConfiguration());
	   joinJob.setJob(job1);
	   ControlledJob aggrJob = new ControlledJob(job2.getConfiguration());
	   aggrJob.setJob(job2);
	   
	   //添加依赖
	   aggrJob.addDependingJob(joinJob);
	   
	   JobControl jobControl = new JobControl("ms");
	   jobControl.addJob(joinJob);
	   jobControl.addJob(aggrJob);
	   
	   Thread jobThread = new Thread(jobControl);
	   jobThread.start();
	   while(true){
		   if (jobControl.allFinished()) {
			System.out.println("执行结束");
			jobControl.stop();
			return 0;
		}
		   if (jobControl.getFailedJobList().size()>0) {
			System.out.println("执行失败");
			jobControl.stop();
			return 1;
		}
	   }
   }
   public static void main(String[] args) throws Exception {
	OrderStateSumJobChain();
}
}
