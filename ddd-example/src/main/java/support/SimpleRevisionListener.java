package support;

import org.hibernate.envers.RevisionListener;

public class SimpleRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		
		((SimpleRevisionInfo)revisionEntity).setUser("test").setIp("xxx.xxx.xxx.xxx");
	}

}
