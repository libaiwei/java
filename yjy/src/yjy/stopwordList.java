package yjy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class stopwordList {
	public static List<String> getlist() throws IOException{
		List<String> Stoplist=new ArrayList<String>();
		File L1=new File("/Users/baiweili/Documents/workspace/yjy/百度停用词列表.txt");
		
		List<File> list=new ArrayList<File>();
		list.add(L1);
		String str="";
		for(int i=0;i<1;i++){
			try {
				BufferedReader br = new BufferedReader(new FileReader(list.get(i)));
				while((str = br.readLine())!=null){
					Stoplist.add(str);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	/*	for(int j=0;j<Stoplist.size();j++){
			System.out.print(Stoplist.get(j)+"\n");
		}	*/
		return Stoplist;
		
	}
	
	public static boolean isStopWord(String word,List<String> Stoplist){
		for(int i=0;i<Stoplist.size();i++){
			   if(word.equalsIgnoreCase(Stoplist.get(i)))
				   return true;		
		 }
			return false;
	}

}
