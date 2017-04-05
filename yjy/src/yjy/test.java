package yjy;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;  
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;  
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;  
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
public class test {

	public static void main(String[] args) {
		File dir=new File("/Users/baiweili/Downloads/answer/");
		File[] files=getFileList(dir);
		System.out.print(files);
		
	}
	public static File[] getFileList(File dir){
		List<File> list=new ArrayList<File>();
		getALL(dir,list);
		File[] files=list.toArray(new File[list.size()]);
		return files;		
	}
	public static void getALL(File dir,List<File> list){
		File[] files=dir.listFiles();
		for(File file : files){
			if(file.isDirectory())
				getALL(file,list);
			else{
				list.add(file);	
			}
		}	
	}
}
