import java.io.IOException;
//import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Lettercount {
	public static class Map extends Mapper<Object,Text,Text,IntWritable>
	{
		public static IntWritable one=new IntWritable(1);
		private Text word = new Text();
		public void map(Object key,Text value,Context context) throws IOException,InterruptedException
		{
			String str=value.toString();
			char[] ch=str.toCharArray();
			for(int i=0;i<ch.length;i++)
			{
				if(ch[i]<=126&&ch[i]>=33)
				{
					word.set(String.valueOf(ch[i]));
					context.write(word, one);
				}
					
				}
		}
		
	}
	
	public static class Reduce extends Reducer<Text,IntWritable,Text,IntWritable>
	{
		private static IntWritable result=new IntWritable();
		public void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException,InterruptedException
		{
			int sum=0;
			for(IntWritable val : values)
			{
				sum+=val.get();
			}
			
			result.set(sum);
			context.write(key, result);
			
		}
	}
	
	public static void main (String [] args) throws Exception
	{
		Configuration conf = new Configuration();
		String[] otherArgs = new GenericOptionsParser(conf,args).getRemainingArgs();
		if (otherArgs.length!=2)
		{
			System.err.println("Usage Lettercount <int> <out>");
			System.exit(2);
			
		}
		Job job = new Job(conf,"letter count");
		job.setJarByClass(Lettercount.class);
		job.setMapperClass(Map.class); //Ϊjob����Mapper�� 
		job.setCombinerClass(Reduce.class); //Ϊjob����Combiner��  
		job.setReducerClass(Reduce.class); //Ϊjob����Reduce��   
		job.setOutputKeyClass(Text.class);        //�������key������
		job.setOutputValueClass(IntWritable.class);//  �������value������
		FileInputFormat.addInputPath(job, new Path(otherArgs[0])); //Ϊmap-reduce��������InputFormatʵ����   ��������·��
		    
		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));//Ϊmap-reduce��������OutputFormatʵ����  �������·��
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
