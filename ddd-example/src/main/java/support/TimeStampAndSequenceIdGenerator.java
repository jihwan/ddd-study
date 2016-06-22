package support;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

public class TimeStampAndSequenceIdGenerator implements IdentifierGenerator {

	private static Logger log = LoggerFactory.getLogger(TimeStampAndSequenceIdGenerator.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * <pre>
	 * 아래 구현은 공용 사용을 하기 위한 방법이다.
	 * 따라서 아래와 같은 규약을 지켜야 한다!!!
	 * 
	 * sequence 명은 ${tableName}_SEQ 명으로 작성해야 한다.
	 * {@link Entity} class는 {@link Table}에 table name을 반드시 명시해야 한다.
	 */
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		
		String tableName = extractTableName(object);
		
		Connection connection = session.connection();
		
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT " + tableName + "_SEQ.NEXTVAL AS SEQNO");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("SEQNO");
				String code = format.format(new Date()) + StringUtils.leftPad("" + id, 5, '0');
				return new BigDecimal(code).longValue();
			}
		} catch (SQLException e) {
			log.error("Unable to generate Sequence", e);
			throw new HibernateException("Unable to generate Sequence");
		}
		
		return null;
	}

	/**
	 * <pre>
	 * object 에서 {@link Table} annotation 정보를 추출하고, table 명을 리턴한다.
	 * caution : object가 Collection(idbag)인 경우는 테스트를 못하였으며, 이상 작동 할 여지가 있다!!!
	 * 
	 * @param object
	 * @return
	 */
	protected String extractTableName(Object object) throws IllegalArgumentException {
		
		try {
			Table tableAnnotation = AnnotationUtils.findAnnotation(object.getClass(), Table.class);
			return tableAnnotation.name();
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable extract table name");
		}
	}

}
