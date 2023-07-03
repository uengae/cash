package cash.vo;

public class Member {
	private String memberId;
	private String memberpw;
	private String updatedate;
	private String createdate;
	
	public Member() {
		super();
	}
	
	public Member(String memberId, String memberpw, String updatedate, String createdate) {
		super();
		this.memberId = memberId;
		this.memberpw = memberpw;
		this.updatedate = updatedate;
		this.createdate = createdate;
	}

	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberpw() {
		return memberpw;
	}
	public void setMemberpw(String memberpw) {
		this.memberpw = memberpw;
	}
	public String getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(String updatedate) {
		this.updatedate = updatedate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	
	
}
