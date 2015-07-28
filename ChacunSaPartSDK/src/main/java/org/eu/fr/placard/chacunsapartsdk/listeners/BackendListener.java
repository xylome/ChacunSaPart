package org.eu.fr.placard.chacunsapartsdk.listeners;

import org.eu.fr.placard.chacunsapartsdk.exceptions.BackendException;
import org.eu.fr.placard.chacunsapartsdk.beans.BackendObject;

public interface BackendListener {
	public void onBackendResponse(BackendObject bo);
	
	public void onBackendError(BackendException be);
}
