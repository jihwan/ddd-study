package support;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <pre>
 * 이 테이블의 경우, 용량이 엄청나게 커진다.
 * 따라서, partitioning을 반드시 해야 한다.
 * 
 * h2database sequence grammer
 * 
 * CREATE SEQUENCE IF NOT EXISTS REVINFO_SEQ START WITH 1 MAXVALUE 99999 CYCLE;
 * SELECT REVINFO_SEQ.NEXTVAL;
 * 
 * @author Jihwan Hwang
 *
 */
@Entity @Table(name = "REVINFO")
@org.hibernate.envers.RevisionEntity(SimpleRevisionListener.class)
public class SimpleRevisionInfo {

	@Id
	@org.hibernate.annotations.GenericGenerator(name="revinfo_seq_id", strategy="support.TimeStampAndSequenceIdGenerator")
	@GeneratedValue(generator="revinfo_seq_id")
	@Column(length=2, precision=20)
	@org.hibernate.envers.RevisionNumber
	private Long id;
	
	@org.hibernate.envers.RevisionTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REVTSTMP")
    private Date timestamp;

	/**
	 * 추가적으로 audit user, ip 등을 넣어
	 * 변경 이력에 대한 정보를 풍성하게 할 수 있다. 
	 */
	private String user;
	
	private String ip;
	
	SimpleRevisionInfo() {}

	SimpleRevisionInfo setUser(String user) {
		this.user = user;
		return this;
	}
	
	SimpleRevisionInfo setIp(String ip) {
		this.ip = ip;
		return this;
	}
	
	public Long getId() {
		return id;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getIp() {
		return ip;
	}
}
