package abcimpl;

import org.springframework.stereotype.Service;

import ltw.DomainService;

@Service
public class DomainServiceImpl implements DomainService {

	@Override
	public void execute() {
		System.err.println("called execute.");
	}

}
