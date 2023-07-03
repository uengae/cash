package cash.vo;

public class Cashbook {

	private int cashbookNo; 
	private String category;
	private String cashbookDate;
	private String memo;
	private int price;
	private String updatedate;
	private String createdate;
	public Cashbook() {
		super();
	}
	
	public Cashbook(int cashbookNo, String category, String cashbookDate, String memo, int price, String updatedate,
			String createdate) {
		super();
		this.cashbookNo = cashbookNo;
		this.category = category;
		this.cashbookDate = cashbookDate;
		this.memo = memo;
		this.price = price;
		this.updatedate = updatedate;
		this.createdate = createdate;
	}
	
	public int getCashbookNo() {
		return cashbookNo;
	}
	public void setCashbookNo(int cashbookNo) {
		this.cashbookNo = cashbookNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCashbookDate() {
		return cashbookDate;
	}
	public void setCashbookDate(String cashbookDate) {
		this.cashbookDate = cashbookDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
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
