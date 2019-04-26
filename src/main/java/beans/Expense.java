package beans;

public class Expense {
	private String id;
	private String appDate;
	private String updateDate;
	private String applicant;
	private String title;
	private int amount;
	private String status;
	private String payee;
	private String changer;

	public String getId(){
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getAppDate(){
		return appDate;
	}
	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getUpdateDate(){
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getApplicant(){
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getTitle(){
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getAmount(){
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStatus(){
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayee(){
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getChanger(){
		return changer;
	}
	public void setChanger(String changer) {
		this.changer = changer;
	}
}
