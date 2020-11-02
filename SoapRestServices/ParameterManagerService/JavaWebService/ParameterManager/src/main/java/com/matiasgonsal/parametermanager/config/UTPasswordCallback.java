package com.matiasgonsal.parametermanager.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;

public class UTPasswordCallback implements CallbackHandler {
	
	private Map<String, String> userPasswords;
	
	public UTPasswordCallback() {
		if (this.userPasswords == null) {
			this.userPasswords = new HashMap<>();
			this.userPasswords.put("Admin", "Admin");
			this.userPasswords.put("Matias", "123");
		}
	}

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		
		for (Callback callback : callbacks) {
			String userIdentifier = ((WSPasswordCallback)callback).getIdentifier();
			String storedPassword = this.userPasswords.get(userIdentifier);
			if (storedPassword != null) {
				((WSPasswordCallback)callback).setPassword(storedPassword);
				return;
			}
		}
	}

}
