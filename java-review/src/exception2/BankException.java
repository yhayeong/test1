package exception2;

public class BankException extends Exception {
	//github에서 수정
	
	

	//필드
	BankError errCode;
	
	//생성자
	public BankException(String message, BankError errCode) {
		super(message);
		this.errCode = errCode;
	}

	//메소드
	//예외를 처리하는 곳에서 에러메시지를 표시하기위한 메소드
	@Override
	public String toString() {
		//생성된 BankException객체의 에러 코드에 따라 메시지를 다르게 반환
		
		String msg = "[" + super.getMessage() + "]"; //생성자에서 초기화된 메시지에
		
		switch (errCode) { //에러코드에 맞는 문구를 추가한다
		case NOID : msg += "없는 계좌번호입니다."; break;
		case EXISTID : msg += "이미 존재하는 계좌번호입니다."; break;
		case LACK : msg += "잔액이 부족합니다."; break;
		case MINUS : msg += "입력금액이 잘못되었습니다."; break;
		case MENU : msg += "메뉴를 잘못 선택하셨습니다."; break;
		case NOTAGRADE : msg += "등급을 잘못 입력하셨습니다."; break;
		default: msg += "일반 오류입니다."; break;
		}
		
		return msg;
	} 
	
	
}
