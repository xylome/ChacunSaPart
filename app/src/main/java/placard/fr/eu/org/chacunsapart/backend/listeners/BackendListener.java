package placard.fr.eu.org.chacunsapart.backend.listeners;

import placard.fr.eu.org.chacunsapart.backend.beans.BackendObject;
import placard.fr.eu.org.chacunsapart.backend.exceptions.BackendException;

public interface BackendListener {
	public void onBackendResponse(BackendObject bo);
	
	public void onBackendError(BackendException be);
}
