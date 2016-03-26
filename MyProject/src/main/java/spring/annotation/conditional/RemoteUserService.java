package spring.annotation.conditional;

import org.springframework.stereotype.Service;

@Remote
@Service
public class RemoteUserService implements UserService {

	public void say() {
		System.out.println("this is RemoteUserService ");
	}

}