package exception2;

import java.util.Scanner;

public class Account {
	String id;
	String name;
	int balance;
	
	public Account() {}
	public Account(String id, String name, int money) {
		this.id = id;
		this.name = name;
		this.balance = money;
	}
	
	public String info() {
		return String.format("계좌번호:%s, 이름:%s, 잔액:%d", id, name, balance);
	}
	
	public void deposit(int money) throws BankException {
		if(money<=0) {
			throw new BankException("입금금액오류", BankError.MINUS);
		}
		balance += money;
		System.out.println("*" + money + "원을 입금하셨습니다.");
	}
	
	public void withdraw(int money) throws BankException { 
		if(balance<money) throw new BankException("잔액부족", BankError.LACK);
		else if(money<0) throw new BankException("금액입력오류", BankError.MINUS);
		balance -= money;
		System.out.println("*" + money + "원을 출금하셨습니다.");
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() { 
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getBalance() {
		return balance;
	}
	
}

