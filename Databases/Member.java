/**
 * Class to store gym members details
 * @author 2031944H
 *
 */
public class Member {

	//Instance variables
	private String fname, sname;
	private int id;
	
	public Member(String fname, String sname, int id) {
		
		//Set instance variables
		this.fname = fname;
		this.sname = sname;
		this.id = id;
	}
	
	/**
	 * get the members first name
	 * @return first name
	 */
	public String getFName() {
		
		return fname;
	}
	
	/**
	 * Get the members surname
	 * @return surname
	 */
	public String getSName() {
		
		return sname;
	}
	
	/**
	 * Get the members ID
	 * @return member ID
	 */
	public int getID() {
		
		return id;
	}
}
