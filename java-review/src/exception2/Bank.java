package exception2;
import java.util.Scanner;

import exception.Account; //Bank와 다른 패키지에 있는 Account클래스 사용
import exception.SpecialAccount;

//예외처리를 위한 BankException 클래스를 만들고 그를 통한 예외처리 코드를 작성할것 

public class Bank {
	
	Account[] accs = new Account[100];
	int accCnt; //개설된계좌개수
	
	Scanner sc = new Scanner(System.in);
	
	int menu() throws BankException {
		System.out.println("[코스타 은행]");
		System.out.println("1. 계좌개설");
		System.out.println("2. 입금");
		System.out.println("3. 출금");
		System.out.println("4. 계좌조회");
		System.out.println("5. 전체계좌조회");
		System.out.println("0. 종료");
		System.out.print("선택> ");
//		return Integer.parseInt(sc.nextLine()); 
		//여기서 엔터를 누르거나 숫자가 아닌값을 입력시 NumberFormatException이 발생한다 그러므로 
		//0~5를 벗어난 숫자를 입력했을 경우에도 예외처리하기 위해서 BankException클래스를 생성함
		
		int sel = Integer.parseInt(sc.nextLine());
		if(!(0<=sel&&sel<=5)) throw new BankException("메뉴선택오류", BankError.MENU);
		//특정경우에 에러를 발생시키고 호출부로 처리를 위임한다
		return sel;
	}
	

	void selAccMenu() throws BankException {
		System.out.println("[계좌개설]");
		System.out.println("1. 일반계좌");
		System.out.println("2. 특수계좌");
		System.out.print("선택> ");
		int sel = Integer.parseInt(sc.nextLine());
		if(sel==1) makeAccount();
		else if(sel==2) makeSpecialAccount();
		else throw new BankException("메뉴선택오류", BankError.MENU);
	}
	
	
	void makeAccount() throws BankException {
		System.out.println("[일반계좌 개설]");
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		
		Account searchedAcc = searchAccById(id);
		if(searchedAcc!=null) throw new BankException("계좌번호오류", BankError.EXISTID);
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		System.out.print("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());
		if(money<0) throw new BankException("금액입력오류", BankError.MINUS);
		accs[accCnt++] = new Account(id, name, money);
	}
	
	
	void makeSpecialAccount() throws BankException {
		System.out.println("[특수계좌 개설]");
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		
		Account acc = searchAccById(id);
		if(acc!=null) if(acc!=null) throw new BankException("계좌번호오류", BankError.EXISTID);
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		System.out.print("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());
		if(money<0) throw new BankException("금액입력오류", BankError.MINUS);
		System.out.print("등급(VIP-V, Gold-G, Silver-S, Normal-N) : ");
		String grade = sc.nextLine(); 
		
		accs[accCnt++] = new SpecialAccount(id, name, money, grade); //이곳이 위임받았는데 이곳에서도 예외처리하지않으면서 main으로 위임처리됨
		//유효하지 않은 값이 입력됐을 경우, SpecialAccount클래스의 setGrade와 생성자에서 예외를 발생시키고 호출부로 위임함 @@@예외발생되지않음
	}
	
	
	Account searchAccById(String id) {
		for (int i = 0; i < accCnt; i++) {
			if(accs[i].getId().equals(id)) {
				return accs[i]; //찾았다면 그걸 가지고 호출부로 간다(바로메소드종료)
			}
		}
		return null; 
	}
	
	
	void deposit() throws BankException {
		System.out.println("[입금]");
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		
		Account acc = searchAccById(id); //입력한 id가 없다면 null값을 반환받을것
		if(acc ==null) throw new BankException("계좌번호오류", BankError.NOID);
		
		System.out.print("입금액 : ");
		int money = Integer.parseInt(sc.nextLine());
		acc.deposit(money);
		//Account의 deposit메소드를 호출하므로 Account클래스에서 예외를 발생시킨다
	}
	
	void withdraw() {
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		
		Account acc = searchAccById(id);
		if(acc == null) {
			System.out.println("계좌번호가 틀립니다.");
			return;
		}
		
		System.out.print("출금액 : ");
		int money = Integer.parseInt(sc.nextLine());
		if(acc.getBalance() < money) {
			System.out.println("잔액:" + acc.getBalance() + ", 입력금액: " + money + "\n*잔액이 부족합니다.");
			return;
		} else {
			acc.withdraw(money);
		}
		
	}
	
	void accountInfo() {
		System.out.print("계좌번호 : ");
		String id = sc.nextLine();
		Account acc = searchAccById(id);
		if(acc==null) {
			System.out.println("계좌번호가 틀립니다.");
			return;
		}
		System.out.println(acc.info());
	}
	
	void allAccountInfo() {
		System.out.println("개설계좌수: " + accCnt);
		
		for (int i = 0; i < accCnt; i++) {
			System.out.println(accs[i].info());
		}
	}
	
	
	
	public static void main(String[] args) { 

		Bank bank = new Bank();
		int sel;
		
		while(true) {
			try {
				sel = bank.menu();
				if(sel==0) break;
				switch(sel) {
				case 1: bank.selAccMenu(); break;
				case 2: bank.deposit(); break;
				case 3: bank.withdraw(); break;
				case 4: bank.accountInfo(); break;
				case 5: bank.allAccountInfo(); break;
				}
			} catch (NumberFormatException e) {
				System.out.println("입력형식이 맞지 않습니다. 다시 입력해주세요.");
				
			} catch (BankException e) { //더 좁은 예외를 밑으로 작성
				System.out.println(e.toString());
			}
				
		}//while
		
		
	}//main

}//Bank