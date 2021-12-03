package exp3;
import java.util.Scanner;
import java.util.Stack;

public class LL1 {
	/*
	 * ��ÿ���ս���ͷ��ս��������һ�����ִ���
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
	
	public void analysis(){//Ԥ���������
		while(true){
			System.out.println("������һ����#�������ַ���(ֱ������#�˳�)");
			Scanner reader = new Scanner(System.in);
			String str = reader.nextLine();
			char[] ch = str.toCharArray();//�Ѷ��������ݴ����ַ�����
			if(ch[0] == '#') break;//���ֻ����һ��#���˳�����
			Integer[][] M = new Integer[16][16];//LL(1)Ԥ�������
			for(int i = 0; i<16; i++)//��ʼȫ��0
				for(int j = 0; j<16; j++)
					M[i][j]=0;
			M[8][0] = 1;//���ս��E���ս��i�еı�����ݸ�ֵΪ1
			M[8][5] = 1;//���ս��E���ս��(�еı���������Ͼ���ͬ��Ҳ��ֵΪ1
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
			s.push(EOS);//������ջ��push#
			s.push(NTS_E);//��ջ��push��ʼ����E
			while(s.size()>0){//��ջ����ʱѭ��
				for(int i = 0; i < ch.length;){
					if(lexer(ch[i])==Integer.parseInt(s.peek().toString())){//�ַ���ָ��ָ���ַ���ջ����ͬ
						i++;//�ַ���ָ�����
						s.pop();//��ջ
					}
					else{
						switch(M[(int)s.peek()][lexer(ch[i])]){//ѡ��LL(1)���������Ŀ����Ϊջ��Ԫ�أ���Ϊ�ַ������ַ�
						case 1:
							s.pop();//ջ�ڷ��ų�ջ
							s.push(NTS_EE);//����ʽ�е�Ԫ�ص�����ջ
							s.push(NTS_T);
							break;
						case 2:
							s.pop();
							s.push(NTS_EE);
							s.push(NTS_T);
							s.push(NTS_A);
							break;
						case 3:
							s.pop();//����ʽΪ�ţ�ֻջ�ڷ��ų�ջ
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
							System.out.println("�������");
							return;
						}
					}
				}
			}
			System.out.println("�����ȷ��");
		}
	}
}
