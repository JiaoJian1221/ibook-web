package me.jiaojian;

import me.jiaojian.controller.ChannelController;
import me.jiaojian.controller.JdController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IBookApplicationTests {


	@Autowired
	private JdController jdController;

	@Test
	public void testJd() throws IOException {
		//jdController.getChannels();
		//jdController.getCatalogs();
		jdController.getBooks(885L);
	}


}
