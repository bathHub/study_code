package homework;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocInfoOutput {
	private static ArrayList<String> filelist = new ArrayList<String>();
    public static List<String> filenames = new ArrayList<String>();
    
   static String ss = "";
	public static void main(String[] args) throws Exception {
//文件名   修改日期   文件类型    文件的大小
		String filePath = "E:\\homeworkDoc";
		  List<String> list = getFiles(filePath);
		  int i=0;
		  for (String string : list) {
			  if (!(string.contains("."))) {
				  string = string+"wenjianjia";
				 
			} ss=ss+string+".";
			  
			  i++;
		}
		ss= ss.substring(0, ss.length()-1);
		  System.out.println(i);
		  //System.out.println(ss);
		 // System.out.println(ss);
		  String[] strings = ss.split("//.");
		  for (int j=0;j<strings.length;j++) {
			System.out.println(strings[j]);
		}
	} 
	/*      
	 * 通过递归得到某一路径下所有的目录及其文件
	 */
	static List<String> getFiles(String filePath){
		File root = new File(filePath);
		File[] files = root.listFiles();
		for(File file:files){ 
			
			if(file.isDirectory()){

				getFiles(file.getAbsolutePath());
				filelist.add(file.getAbsolutePath());
			//	System.out.println("显示"+filePath+"下所有子目录及其文件"+file.getAbsolutePath());
				
			      
			// System.out.println(file.getName());
				/*String[] strings = file.getName().split(".");
				for (int i = 0; i < strings.length; i++) {
					System.out.print(strings[i]+"\t");
				}*/
			}
			//	System.out.println("显示"+filePath+"下所有子目录"+file.getAbsolutePath());
//	System.out.print(file.getName());
				/*String[] strings = file.getName().split(".");
				for (int i = 0; i < strings.length; i++) {
					System.out.print(strings[i]+"\t");
				}*/
				
			/*	
			if (file.getName().contains(".")) {
					ss.
					for (String string : ss) {
						System.out.print(string);
						System.out.println();
					}				
					}
				else{
					System.out.print(file.getName());
				
				
				ss =  file.getName().split(".");
				for (int i = 0; i < ss.length; i++) {
					System.out.println(ss[i]);
				}
		    
			}*/   
			filenames.add(file.getName());
		
				
				
			
	
			  
}
		/*for (int i = 0; i < mc.size(); i++) {
 			System.out.println(mc.get(i));*/
		 return filenames;		
		}  
	}
