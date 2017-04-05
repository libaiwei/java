package yjy;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.FileWriter;  
import java.io.IOException;
import java.io.InputStreamReader;
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

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IKAnalyzer {
	
	@SuppressWarnings("resource")

	
	public static void main(String[] args)throws IOException{   
		File dir=new File("/Users/baiweili/Desktop/weibo/data/data2");
		File[] files=getFileList(dir);
		List<String> wordlist=new ArrayList<String>();
		for(int i=1;i<files.length;i++){
			File f=files[i];
			String [] aa = files[i].toString().split("/");
			String name = aa[aa.length-1];
			File destfile=new File("/Users/baiweili/Desktop/output_1",name);
			Segment(f,destfile,wordlist);
		}
		System.out.print("ok");
		Map<String,Integer> wordtimes=new HashMap<String,Integer>();
		Set<String> set =wordtimes.keySet();
		for(int j=0;j<wordlist.size();j++){
			boolean flag=false;
			for(Iterator it = set.iterator();it.hasNext();){
			   String key = (String) it.next();
			   Integer value = (Integer)wordtimes.get(key);	
			   if(wordlist.get(j).equalsIgnoreCase(key)){
				   value++;
				   wordtimes.put(key,value);
				   System.out.print(key+value+"\n");
				   flag=true;
				   break;
			   }
			}
			if(flag==false){
				wordtimes.put(wordlist.get(j), 1);
			}
		}
		System.out.print("ok");
		File wordmap=new File("/Users/baiweili/Desktop/output_1","count.txt");
		BufferedWriter wri=null;
        List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(wordtimes.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String,Integer>>() {
            //????????
            public int compare(Entry<String, Integer> o1,
                    Entry<String,Integer > o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
		try{
			wri= new BufferedWriter(new FileWriter(wordmap));
			 for(Map.Entry<String,Integer> mapping:list){ 
	               System.out.println(mapping.getKey()+":"+mapping.getValue()); 
	               String key = (String) mapping.getKey();
				   Integer value = mapping.getValue();	
					// System.out.print(s+value);
					 if(value>5){
						 String characterSet = "UTF-8";
						 String str = key.toString()+" "+value.toString();
//						 str = new String(str.getBytes(characterSet), characterSet);
						 wri.write(str);
						 wri.newLine();
					 }
	          } 
		}
		finally{
			try{
				if(wri!=null)
				   wri.close();
			}
			catch(IOException e){
				throw new RuntimeException("error");
			}
		}	
		System.out.print("ok");	
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
	public static void Segment(File f,File destfile,List<String> wordlist) throws IOException
	{
		BufferedWriter bufw=null;
		String result;
	//	List<String> flist=new ArrayList<String>();
		Map<String,Integer> words=new HashMap<String,Integer>();
		Set<String> set =words.keySet();
		try{
				result="";
				bufw= new BufferedWriter(new FileWriter(destfile));
//				BufferedReader br = new BufferedReader(new FileReader(f));
				InputStreamReader isr = new InputStreamReader(new FileInputStream(f), "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				String str = null;
				List<String> ll=stopwordList.getlist();
				while((str = br.readLine())!=null){
						result = result + str;
				}
				StringReader sr=new StringReader(result);  
			    IKSegmenter ik=new IKSegmenter(sr, true);  
			    Lexeme lex=null;  
			    String s=null;
			    while((lex=ik.next())!=null){  
			    	    boolean flag=true;
			    	    //  System.out.print(lex.getLexemeText()+"|"); 
			    	    s=lex.getLexemeText();
			    	    if(isChinese(s)){
			    	    	    if(!stopwordList.isStopWord(s, ll)){
			    	    	    	   /* for(int i=0;i<flist.size();i++){
			    	    	    	    	      if(s.equalsIgnoreCase(flist.get(i))){
			    	    	    	    	    	      flag=false;
			    	    	    	    	    	      break;
			    	    	    	    	      }
			    	    	    	     }*/
			    	    	     	
			    	    	     	for(Iterator it = set.iterator();it.hasNext();){
			    		     	     String key = (String) it.next();
			    			      	 Integer value = (Integer)words.get(key);
			    				     if(s.equalsIgnoreCase(key)){
			    				    	      value++;
			    				    	      words.put(key, value);
	    	    	    	    	    	              flag=false;
	    	    	    	    	    	              break;
	    	    	    	    	              }
			    	    	     	}
			    	    	     	if(flag==true){
			    	    	     		words.put(s, 1);
			    	    	     		wordlist.add(s);
			    	    	     	}
			    	    	    	  /*   if(flag==true){
			    	    	    	    	       flist.add(s);
			    	    	    	    	       bufw.write(s);
								   bufw.newLine();
								   wordlist.add(s);
			    	    	    	     }  */
					    	 }
			    	    }
			    }
				for(Iterator it = set.iterator();it.hasNext();){
		     	     String key = (String) it.next();
			      	 Integer value = (Integer)words.get(key);
			      	 bufw.write(key+" "+value);
					 bufw.newLine();
				    
	    	     	}
		}
		finally{
				try{
					if(bufw!=null)
					   bufw.close();
				}
				catch(IOException e){
					throw new RuntimeException("error");
				}
				
		}
		
	}
	 private static boolean isC(char c) {
	        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
	                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
	                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
	            return true;
	        }
	        return false;
	    }
	 
	    public static boolean isChinese(String strName) {
	        char[] ch = strName.toCharArray();
	        for (int i = 0; i < ch.length; i++) {
	            char c = ch[i];
	            if (!isC(c)) {
	                return false;
	            }
	        }
	        return true;
	    }
}