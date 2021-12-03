package exp3;
import java.util.Scanner;
import java.util.Stack;

public class LL1 {
	/*
	 * 给每个终结符和非终结符都赋予一个数字代替
	 */
	public static final int TS_I = 0;
	public static final int TS_PLUS = 1;
	public static final int TS_SUBTRACT = 2;
	public static final int TS_MULTIPLY = 3;
	public static final int TS_DIVISION = 4;
	public static final int TS_L_PARENS = 5;
	public static final int TS_R_PARENS = 6;
	public static final int EOS = 7;
	public static final int NTS_E = 8;
	public static final int NTS_EE = 9;
	public static final int NTS_T = 10;
	public static final int NTS_TT = 11;
	public static final int NTS_F = 12;
	public static final int NTS_A = 13;
	public static final int NTS_M = 14;
	public static final int TS_INVALID = 15;

	public static void main(String args[]){
		new LL1().analysis();
	}

	public int lexer(char c){
		switch(c){
		case 'i':return TS_I;
		case '+':return TS_PLUS;
		case '-':return TS_SUBTRACT;
		case '*':return TS_MULTIPLY;
		case '/':return TS_DIVISION;
		case '(':return TS_L_PARENS;
		case ')':return TS_R_PARENS;
		case '#':return EOS;
		default:return TS_INVALID;
		}
	}
	
	public void analysis(){//预测分析程序
		while(true){
			System.out.println("请输入一个以#结束的字符串(直接输入#退出)");
			Scanner reader = new Scanner(System.in);
			String str = reader.nextLine();
			char[] ch = str.toCharArray();//把读到的内容存入字符数组
			if(ch[0] == '#') break;//如果只输入一个#就退出程序
			Integer[][] M = new Integer[16][16];//LL(1)预测分析表
			for(int i = 0; i<16; i++)//初始全部0
				for(int j = 0; j<16; j++)
					M[i][j]=0;
			M[8][0] = 1;//非终结符E行终结符i列的表格内容赋值为1
			M[8][5] = 1;//非终结符E行终结符(列的表格内容与上句相同，也赋值为1
			M[9][1] = 2;
			M[9][2] = 2;
			M[9][6] = 3;
			M[9][7] = 3;
			M[10][0] = 4;
			M[10][5] = 4;
			M[11][1] = 5;
			M[11][2] = 5;
			M[11][3] = 6;
			M[11][4] = 6;
			M[11][6] = 7;
			M[11][7] = 7;
			M[12][0] = 8;
			M[12][5] = 9;
			M[13][1] = 10;
			M[13][2] = 11;
			M[14][3] = 12;
			M[14][4] = 13;
			Stack s = new Stack();
			s.push(EOS);//首先向栈中push#
			s.push(NTS_E);//向栈中push开始符号E
			while(s.size()>0){//当栈不空时循环
				for(int i = 0; i < ch.length;){
					if(lexer(ch[i])==Integer.parseInt(s.peek().toString())){//字符串指针指向字符和栈顶相同
						i++;//字符串指针后移
						s.pop();//出栈
					}
					else{
						switch(M[(int)s.peek()][lexer(ch[i])]){//选择LL(1)分析表的项目，行为栈顶元素，列为字符串的字符
						case 1:
							s.pop();//栈内符号出栈
							s.push(NTS_EE);//生成式中的元素倒序入栈
							s.push(NTS_T);
							break;
						case 2:
							s.pop();
							s.push(NTS_EE);
							s.push(NTS_T);
							s.push(NTS_A);
							break;
						case 3:
							s.pop();//生成式为ε，只栈内符号出栈
							break;
						case 4:
							s.pop();
							s.push(NTS_TT);
							s.push(NTS_F);
							break;
						case 5:
							s.pop();
							break;
						case 6:
							s.pop();
							s.push(NTS_TT);
							s.push(NTS_F);
							s.push(NTS_M);
							break;
						case 7:
							s.pop();
							break;
						case 8:
							s.pop();
							s.push(TS_I);
							break;
						case 9:
							s.pop();
							s.push(TS_R_PARENS);
							s.push(NTS_E);
							s.push(TS_L_PARENS);
							break;	
						case 10:
							s.pop();
							s.push(TS_PLUS);
							break;	
						case 11:
							s.pop();
							s.push(TS_SUBTRACT);
							break;	
						case 12:
							s.pop();
							s.push(TS_MULTIPLY);
							break;	
						case 13:
							s.pop();
							s.push(TS_DIVISION);
							break;	
						default:
							System.out.println("语句有误！");
							return;
						}
					}
				}
			}
			System.out.println("语句正确！");
		}
	}
}
