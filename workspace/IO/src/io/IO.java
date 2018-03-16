package io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {
	  public static void main(String[] args) throws IOException {
		  
		  //1.ͨ��filereader��ʵ�ֶ�ȡ�ĵ��е�����
		   File src = new File("a.txt");
		  FileReader fis = new FileReader(src);
		  //2.ͨ��buffer��������߶�ȡ��Ч��
		  BufferedReader bReader = new BufferedReader(fis);
		  
		   
		   int len = 0;
		   while((len = bReader.read())!=-1){
			   
			   System.out.print((char)len);
		   }
		   
		   fis.close();
		
		   //ͨ���ֽڵķ�ʽ��ȡ
		   File src1 = new File("b.txt");
		   //ͨ��fileInputStream����ȡ�ļ��е��ֽ���
		FileInputStream fis1 = new FileInputStream(src1);
		//ͨ��buffer������������ļ��Ķ�ȡЧ��
		BufferedInputStream bStream = new BufferedInputStream(fis1);
		//InputStreamReader�ǰ��ֽ���ת�����ַ���������
		InputStreamReader reader = new InputStreamReader(bStream);
		
		   
		   int by = 0;
		   while((by=reader.read())!=-1){
			       System.out.print((char)by);
		   }
		   
		  fis1.close();
		  
		  
		  
		  
		  File src2 = new File("a.txt");
		  File des2 = new File("b.txt");
		 FileReader fReader = new FileReader(src2);
		 BufferedReader bufferedReader = new BufferedReader(fReader);
		 
		 int len1=0;
		 char[] by1 = new char[1024];
		 while((len1=bufferedReader.read(by1))!=-1){
			    System.out.println(by1);
			 
		 }
		 
		 
		 FileWriter fileWriter = new FileWriter(des2);
		 fileWriter.write(by1);
		 fileWriter.flush();
		 fileWriter.close();
	}

}
