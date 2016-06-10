package ltw;

import javax.persistence.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import abcimpl.DomainServiceImpl;

@Configurable(preConstruction=true) @Entity
public class Domain {

	@Autowired
	transient DomainServiceImpl domainService;
	
	public Domain() {
		domainService.execute();
	}
}
