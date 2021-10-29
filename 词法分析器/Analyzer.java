package exp1;

import java.io.*;
import java.util.*;

public class Analyzer {
	public static String codeScanner(String txtPath){//从文件输入，函数参数为目录
		File file = new File(txtPath);
		if(file.isFile() && file.exists()){
			try{
				FileInputStream fileInputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
				BufferedReader bufferedreader = new BufferedReader(inputStreamReader);
				StringBuffer sb = new StringBuffer();
				String text = null;
				while((text = bufferedreader.readLine()) != null){
					sb.append(text);
				}
				return sb.toString();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String codeScanner(Scanner in){ //从键盘输入，输入#停止
		System.out.println("Please enter your code here:");
		StringBuffer sb = new StringBuffer();
		String s = null;
		while(true){
			s = in.next();
			if(s.equals("#"))
				break;
			else{
				sb.append(s+" ");
			}
		}
		return sb.toString();
	}
	
	public static String[] stringSplitting(String src){//把读入的字符串以空格为标识符存入字符串数组
		String[] segment = src.split(" ");
		return segment;
	}
	
	public static void show(String[] segment){ //输出词法分析结果
		for(String temp : segment){
			System.out.print(temp);
		}
	}
	
	public static boolean isNum(char c){ //我自己编写的判断数字的方法
		return c<='9' && c>='0';
	}
	
	public static boolean isLete(char c){//我自己编写的判断字母的方法
		return c<='z' && c>='a' || c>='A' && c<='Z';
    }
	
	public static MyQueue lexicalAnalyzer(String[] segment){//词法分析器
		MyQueue mq = new MyQueue();
		HashMap dictionary = new HashMap();
		dictionary.put("(", 1);
		dictionary.put(")", 2);
		dictionary.put("{", 3);
		dictionary.put("}", 4);
		dictionary.put(";", 5);
		dictionary.put("=", 6);
		dictionary.put("+", 7);
		dictionary.put("*", 8);
		dictionary.put(">", 9);
		dictionary.put("<", 10);
		dictionary.put(",", 11);
		dictionary.put("'", 12);
		dictionary.put("int", 21);
		dictionary.put("if", 22);
		dictionary.put("then", 23);
		dictionary.put("else", 24);
		dictionary.put("return", 25);
		dictionary.put("main", 26);
		for(int i = 0 ; i < segment.length ; i++ ){//对每一个分割好的字符串数组元素进行分析
			if(dictionary.containsKey(segment[i])) //在字典中存在，可以直接识别
				mq.enqueue(new Token(segment[i],(int)dictionary.get(segment[i])));
			else if(segment[i].matches("[a-zA-Z][a-zA-Z0-9_]*")) //识别变量
				mq.enqueue(new Token(segment[i],0));
			else if(segment[i].matches("\\d+")) //识别常数
				mq.enqueue(new Token(segment[i],20));
			else{       //没用空格分开的那些元素
				char[] chars = segment[i].toCharArray();//把这个字符串转化为字符数组
				String s;
				int j = 0;
				while(j < chars.length){ //对这一个字符数组进行逐个扫描分析
					s = "";
					if(dictionary.containsKey(String.valueOf(chars[j]))){ //在字典中存在的单字符符号，可以直接识别
						mq.enqueue(new Token(String.valueOf(chars[j]),(int)dictionary.get(String.valueOf(chars[j]))));
					    j++;
						continue;
					}
					else if(j+2<chars.length && chars[j] == 'i' && chars[j+1] == 'n' && chars[j+2] == 't' && (j+3==chars.length||!isLete(chars[j+3]) && !isNum(chars[j+3]))){ //识别int，t后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("int",21));
						j=j+3;
						continue;
					}
					else if(j+1<chars.length && chars[j] == 'i' && chars[j+1] == 'f' && (j+2==chars.length||!isLete(chars[j+2]) && !isNum(chars[j+2]))){//识别if，f后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("if",22));
						j=j+2;
						continue;
					}	
					else if(j+3<chars.length &&chars[j] == 't' && chars[j+1] == 'h' && chars[j+2] == 'e' && chars[j+3] == 'n' && (j+4==chars.length || !isLete(chars[j+4]) && !isNum(chars[j+4]))){//识别then，n后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("then",23));
						j=j+4;
						continue;
					}
					else if(j+3<chars.length &&chars[j] == 'e' && chars[j+1] == 'l' && chars[j+2] == 's' && chars[j+3] == 'e' && (j+4==chars.length||!isLete(chars[j+4]) && !isNum(chars[j+4]))){//识别else，e后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("else",24));
						j=j+4;
						continue;
					}
					else if(j+5<chars.length &&chars[j] == 'r' && chars[j+1] == 'e' && chars[j+2] == 't' && chars[j+3] == 'u'  && chars[j+4] == 'r' && chars[j+5] == 'n' && (j+6==chars.length||!isLete(chars[j+6]) && !isNum(chars[j+6]))){//识别return，n后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("return",25));
						j=j+6;
						continue;
					}
					else if(j+3<chars.length &&chars[j] == 'm' && chars[j+1] == 'a' && chars[j+2] == 'i' && chars[j+3] == 'n'&& (j+4==chars.length||!isLete(chars[j+4]) && !isNum(chars[j+4]))){//识别main，n后不能为数字或字母，同时判断不能数组下标越界
						mq.enqueue(new Token("main",26));
						j=j+4;
						continue;
					}
					else if(isNum(chars[j])){  //数字常量
						while(j<chars.length && isNum(chars[j])){
							s = s + chars[j];
							j++;
						}
						mq.enqueue(new Token(s,20));
						continue;
					}
					else if(isLete(chars[j])){  //变量
						while(j<chars.length && (isLete(chars[j])||isNum(chars[j]))){
							s = s + chars[j];
							j++;		
						}
						mq.enqueue(new Token(s,0));
						continue;
					}
					else{//无法识别的符号
						s = s + chars[j];
						j++;
						mq.enqueue(new Token(s,100));
						continue;
					}
				}
			}
		}
		return mq;
	}
	
	public static void show(MyQueue mq){
		System.out.println(mq.toString());
	}
	
	private static class Token{
		private String key;
		private int value;
		public Token(){}
		public Token(String key, int value){
			this.key = key;
			this.value = value;
		}
		public String getKey(){
			return key;
		}
		public void setKey(String key){
			this.key = key;
		}
		public int getValue(){
			return value;
		}
		public void setValue(int value){
			this.value = value;
		}
		public String toString(){
			return "(\"" + key + "\"," + value + ")";
		}
	}
	
	private static class MyQueue{
		private LinkedList data = new LinkedList();
		public boolean isEmpty(){
			return data.isEmpty();
		}
		public int size(){
			return data.size();
		}
		public Object peek(){
			if(this.isEmpty()) throw new
			             NoSuchElementException("Queue underflow");
			return data.getFirst();
		}
		public void enqueue(Object obj){
			data.addLast(obj);
		}
		public Object dequeue(){
			if(this.isEmpty()) throw new
                          NoSuchElementException("Queue underflow");
			return data.removeFirst();
		}
		public String toString(){
			return data.toString();
		}
	}
	
	public static void main(String args[]){
		Scanner in = new Scanner(System.in);
		String src2 = new String(codeScanner(in));
		System.out.println(src2);
		show(lexicalAnalyzer(stringSplitting(src2)));
	}
}