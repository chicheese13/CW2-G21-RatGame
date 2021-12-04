import java.io.*;

public class TestClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String f;
	private String l;
	private String p;
	
	public TestClass (String f, String l, String p) {
		this.f = f;
		this.l = l;
		this.p = p;
	}
	
	public String getProfile() {
		return this.f + " " + this.l + " " + this.p;
	}
}
