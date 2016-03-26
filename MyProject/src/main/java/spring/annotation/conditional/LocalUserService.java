package spring.annotation.conditional;

import org.springframework.stereotype.Service;

@Local
@Service
public class LocalUserService implements UserService {

	public void say() {
		System.out.println("this is LocalUserService ");
	}
}