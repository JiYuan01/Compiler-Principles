package exp2;

import java.util.Scanner;

public class RecursiveDescentParsing extends RuntimeException{
	static char s[];
	static int i;
	static boolean SIGN;//�����жϳ������
	public static void main (String [] main){	
		System.out.println("������һ����䣬��#�Ž�����䣨ֱ������#���˳���");
		while(true){
			SIGN = false;//Ĭ��signΪfalse
			i=0;
			Scanner in = new Scanner(System.in);
			s = in.next().toCharArray();//��������ַ���ת��Ϊ�ַ����飬�������
			if(s[0]=='#')//ֱ������#���˳�
				System.exit(0);
			try {
				P();//���ÿ�ʼ���ŵĺ���P
	        } catch (RecursiveDescentParsing e) {
	            System.out.println("�������!");//����жϳ���ֱ�������ݹ飬���������ʾ
	        }
			if(!SIGN)//���sign��Ϊfalse��˵��������ȷ
				System.out.println("��ȷ��䣡");
			System.out.println("������һ����䣬��#�Ž������");
		}
	}
	
	/**
	 * �ж��Ƿ���follow������
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
	 * ��ÿ�����ս����дһ�������������﷨������д
	 */
	public static void P(){	
		if(!SIGN){//���signΪfalse�ŻῪʼ�˷���
			if(s[i]=='b'){//�����﷨�����������b�����¼���
				++i;//ָ�����
				S();//�����﷨����ִ��S�ķ���
				if(s[i]=='e')//�����﷨�����������e�����¶�
					++i;
			}
			else if(s[i]!='#'){//����������ַ������Ϲ���
				SIGN=true;//��sign��Ϊtrue
				throw new RecursiveDescentParsing();//�����ݹ�
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
