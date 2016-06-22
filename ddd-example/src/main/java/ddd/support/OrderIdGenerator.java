package ddd.support;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * h2database sequence grammer
 * 
 * CREATE SEQUENCE IF NOT EXISTS ORDERS_SEQ START WITH 1 MAXVALUE 99999 CYCLE;
 * SELECT ORDERS_SEQ.NEXTVAL;
 *  
 */
public class OrderIdGenerator implements IdentifierGenerator {

	private static Logger log = LoggerFactory.getLogger(OrderIdGenerator.class);
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		
		// caution : object가 Collection(idbag)인 경우는 테스트를 못하였으며, 이상 작동 할 여기가 있다!!!
		Table tableAnnotation = AnnotationUtils.findAnnotation(object.getClass(), Table.class);
		String tableName = tableAnnotation.name();
		
		Connection connection = session.connection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT " + tableName + "_SEQ.NEXTVAL AS SEQNO");
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("SEQNO");
				String code = format.format(new Date()) + StringUtils.leftPad("" + id, 5, '0');
				return new BigDecimal(code);
			}
		} catch (SQLException e) {
			log.error("Unable to generate Sequence", e);
			throw new HibernateException("Unable to generate Sequence");
		}
		
		return null;
	}

}
