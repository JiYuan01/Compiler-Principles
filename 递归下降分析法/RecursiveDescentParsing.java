package exp2;

import java.util.Scanner;

public class RecursiveDescentParsing extends RuntimeException{
	static char s[];
	static int i;
	static boolean SIGN;//设置判断出错变量
	public static void main (String [] main){	
		System.out.println("请输入一个语句，以#号结束语句（直接输入#号退出）");
		while(true){
			SIGN = false;//默认sign为false
			i=0;
			Scanner in = new Scanner(System.in);
			s = in.next().toCharArray();//将输入的字符串转换为字符数组，逐个分析
			if(s[0]=='#')//直接输入#号退出
				System.exit(0);
			try {
				P();//调用开始符号的函数P
	        } catch (RecursiveDescentParsing e) {
	            System.out.println("语句有误!");//如果判断出错，直接跳出递归，输出错误提示
	        }
			if(!SIGN)//如果sign仍为false，说明输入正确
				System.out.println("正确语句！");
			System.out.println("请输入一个语句，以#号结束语句");
		}
	}
	
	/**
	 * 判断是否在follow集合中
	 */
	public static boolean inPFollow(char ch){	
		return ch=='#';
	}
	
	public static boolean inSFollow(char ch){	
		return ch=='e';
	}
	
	public static boolean inAFollow(char ch){	
		return ch=='i'||ch=='e';
	}
	
	public static boolean inS1Follow(char ch){	
		return ch=='e';
	}
	
	public static boolean inEFollow(char ch){	
		return ch==';'||ch==')';
	}
	
	public static boolean inTFollow(char ch){	
		return ch=='+'||ch=='-'||ch==';'||ch==')';
	}
	
	public static boolean inE1Follow(char ch){	
		return ch==';'||ch==')';
	}
	
	public static boolean inT1Follow(char ch){	
		return ch=='+'||ch=='-'||ch==';'||ch==')';
	}
	
	public static boolean inFFollow(char ch){	
		return ch=='*'||ch=='/'||ch=='+'||ch=='-'||ch==';'||ch==')';
	}
	
	/**
	 * 给每个非终结符号写一个方法，根据语法规则来写
	 */
	public static void P(){	
		if(!SIGN){//如果sign为false才会开始此方法
			if(s[i]=='b'){//根据语法规则，如果读到b就往下继续
				++i;//指针后移
				S();//根据语法规则，执行S的方法
				if(s[i]=='e')//根据语法规则，如果读到e就往下读
					++i;
			}
			else if(s[i]!='#'){//如果读到的字符不符合规则
				SIGN=true;//把sign置为true
				throw new RecursiveDescentParsing();//跳出递归
			}
		}
	}
	
	public static void S(){	
		if(!SIGN){
			A();
		    S1();
		}
	}
	
	public static void S1(){	
		if(!SIGN){
			if(!inS1Follow(s[i]))
		        S();
		    else if(inS1Follow(s[i]));
		    else if(s[i]!='#'){
		    	SIGN=true;
				throw new RecursiveDescentParsing();
		    }
		}
	}
	
	public static void A(){	
		if(!SIGN){
			if(s[i]=='i'){
				++i;
				if(s[i]=='='){
					++i;
					E();
					if(s[i]==';'){
						++i;
					}
					else if(s[i]!='#'){
						SIGN=true;
						throw new RecursiveDescentParsing();
					}	
				}
				else if(s[i]!='#'){
					SIGN=true;
					throw new RecursiveDescentParsing();
				}	
			}
			else if(s[i]!='#'){
				SIGN=true;
				throw new RecursiveDescentParsing();
			}	
		}
	}
	
	public static void E(){	
		if(!SIGN){
			T();
			E1();
		}
	}
	
	public static void E1(){
		if(!SIGN){
			if(s[i]=='+'){
				++i;
				T();
				E1();
			}
			else if(s[i]=='-'){
				++i;
				T();
				E1();	
			}
			else if(inE1Follow(s[i]));
			else if(s[i]!='#'){
				SIGN=true;
				throw new RecursiveDescentParsing();
			}
		}
	}

	public static void T(){
		if(!SIGN){
			F();
			T1();
		}
	}

	public static void T1(){
		if(!SIGN){
			if(s[i]=='*'){
				++i;
				F();
				T1();
			}
			else if(s[i]=='/'){
				++i;
				F();
				T1();
			}
			else if(inT1Follow(s[i]));
			else if(s[i]!='#'){
				SIGN=true;
				throw new RecursiveDescentParsing();
			}
		}
	}

	public static void F(){
		if(!SIGN){
			if(s[i]=='('){
				++i;
				E();
				if(s[i]==')')
					++i;
				else if(s[i]!='#'){
					SIGN=true;
					throw new RecursiveDescentParsing();
				} 
			}
			else if(s[i]=='i')
				++i;
			else if(s[i]!='#'){
				SIGN=true;
				throw new RecursiveDescentParsing();
			}
		}
	}	
}
