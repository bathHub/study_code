package pm;

import java.util.ArrayList;
import java.util.List;


public class ThreadPool {
   private int size=5;//�����̳߳��е��߳���
   List<TaskThread> list = new ArrayList<>();
   //����Ҫ�����Դ˽���Щ��Ŀ���̴߳�������������
   private void init(){
	   for (int i = 0; i < size; i++) {
		 TaskThread taskThread = new TaskThread();
		 taskThread.setName(i+1+"");
		 //����Щ�½��������߳�һ�ηŽ�list��
		 list.add(taskThread);
		 taskThread.start();
		 System.out.println("�߳�"+(i+1)+"�Ѿ�����");
	}
	   
   }

public ThreadPool(int size){
	this.size = size;
	init();
}
public ThreadPool(){
	init();
}
public void addTaskToThread(Task task){
	while (true) {//����ѭ�������̳߳�
		for (TaskThread tt : list) {
			if (!tt.isRunning()) {
				tt.setTask(task);
				return;
			}
		}
	}
}
   public static void main(String[] args) {
	ThreadPool tp = new ThreadPool(10);
	for (int i = 0; i < 100; i++) {
		TaskImpl task = new TaskImpl();
		tp.addTaskToThread(task);
	}
}
}
   
