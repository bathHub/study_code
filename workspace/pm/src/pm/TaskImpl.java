package pm;

public class TaskImpl implements Task{
private int n;

	
	public void setN(int n) {
	this.n = n;
}
public int getN() {
		return n;
	}


	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		System.out.println("����:"+n+"���ڱ��̣߳�"+Thread.currentThread().getName()+"ִ��");
	}

}