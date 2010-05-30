package org.casbah.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

import org.casbah.common.CasbahException;

import com.vaadin.terminal.StreamResource.StreamSource;

public class KeyStoreResource implements StreamSource {
	
	protected InputStream inputStream;
	
	public KeyStoreResource(String keystoreType, char[] keypass, Key privateKey, Certificate...certificateChain)  throws CasbahException {
		try {
			KeyStore ks = KeyStore.getInstance(keystoreType);
			ks.load(null, keypass);
			ks.setKeyEntry("mykey", privateKey, keypass, certificateChain);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ks.store(baos, keypass);
			byte[] data = baos.toByteArray();
			inputStream = new ByteArrayInputStream(data);
			baos.close();			
		} catch (Exception e) {
			throw new CasbahException("Could not create KeyStoreResource", e);
		}
	}
	
	
	@Override
	public InputStream getStream() {
		return inputStream;
	}
}
