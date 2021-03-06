package parkservice.userscore.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Score {

	private long scoreId;
	private long userId;
	private long score1;
	private long score2;
	private long score3;
	
	/**
	 * @return
	 */
	public long getScoreId() {
		return scoreId;
	}
	
	/**
	 * @param scoreID
	 */
	public void setScoreId(long scoreID) {
		this.scoreId = scoreID;
	}
	
	/**
	 * @return
	 */
	public long getUserId() {
		return userId;
	}
	
	/**
	 * @param userID
	 */
	public void setUserId(long userID) {
		this.userId = userID;
	}
	
	/**
	 * @return
	 */
	public long getScore1() {
		return score1;
	}
	public void setScore1(long score1) {
		this.score1 = score1;
	}
	
	/**
	 * @return
	 */
	public long getScore2() {
		return score2;
	}
	
	/**
	 * @param score2
	 */
	public void setScore2(long score2) {
		this.score2 = score2;
	}
	
	/**
	 * @return
	 */
	public long getScore3() {
		return score3;
	}
	
	/**
	 * @param score3
	 */
	public void setScore3(long score3) {
		this.score3 = score3;
	}
}


