package pm;

public class TaskThread extends Thread{
   private Task task;//�����̵߳�����
   private boolean running = false;//�������ִ�о�Ϊtrue
public Task getTask() {
	return task;
}

public boolean isRunning() {
	return running;
}
public void setRunning(boolean running) {
	this.running = running;
}
synchronized public void setTask(Task task) {
	if (!running) {  //����߳��������������У��Ͳ���������
		this.task = task;
		this.running = true;
		this.notify();
	}
	
}

@Override
synchronized public void run() {
	// TODO Auto-generated method stub
	while (true) {try {
		if (!running) {//������߳���û���������񣬾����̴߳��ڵȴ�״̬
			System.out.println("��ǰ�߳�"+Thread.currentThread().getName()+"���ڵȴ�״̬");
			
				this.wait();
			
		}else {
			   Thread.sleep(1000);
			   task.doTask();
			   this.running = true;
		}
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
} 

   
}